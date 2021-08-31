package com.pgy.esdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pgy.esdemo.dto.EsContent;
import com.pgy.esdemo.dto.ReqScrollerDto;
import com.pgy.esdemo.dto.ResScrollerDto;
import com.pgy.esdemo.dto.XmyCoursMainSearchDto;
import com.pgy.esdemo.entity.XmyCoursBodyArtic;
import com.pgy.esdemo.entity.XmyCoursBodyArticParag;
import com.pgy.esdemo.entity.XmyCoursMain;
import com.pgy.esdemo.service.CousrService;
import com.pgy.esdemo.service.IXmyCoursBodyArticParagService;
import com.pgy.esdemo.service.IXmyCoursBodyArticService;
import com.pgy.esdemo.utils.EsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/8/24 11:26
 */
@Slf4j
@Service
public class CousrServiceImpl implements CousrService {
    @Autowired
    private XmyCoursMainServiceImpl xmyCoursMainService;
    @Autowired
    private RestHighLevelClient client;

    @Value("${elasticsearch.cours-name}")
    private String coursName;

    @Autowired
    private EsUtils esUtils;

    @Autowired
    private IXmyCoursBodyArticService xmyCoursBodyArticService;

    @Autowired
    private IXmyCoursBodyArticParagService xmyCoursBodyArticParagService;

    //    字段
    private String[] fields = new String[]{"coursewareName", "content.content"};

    @Override
    public void createCours() {
        Map<String, Object> properties = new HashMap<>();
//        主键
        properties.put("coursewareId", esUtils.getField("keyword"));
//        课件名称
        properties.put("coursewareName", esUtils.getField("text"));
//        封面图
        properties.put("coverImage", esUtils.getField("keyword"));
//        内容模板类型(1图文 2单图 3图视频 4视频)
        properties.put("templateType", esUtils.getField("keyword"));
//         内容下级类型(1分类 2课件)
        properties.put("contentSonType", esUtils.getField("keyword"));
//        审核状态(0待提交 1审核中 2已退回 3待上架 4上架中 5已下架)
        properties.put("auditStatus", esUtils.getField("keyword"));
//        内容
        properties.put("content", esUtils.getField("object"));

        try {
            Boolean contentIk = esUtils.createContentIk(coursName, properties);
            System.out.println(contentIk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOne(String id, String pid) {
        XmyCoursMain byId = xmyCoursMainService.getById(id);
        XmyCoursMainSearchDto xmyCoursMainSearchDto = new XmyCoursMainSearchDto();
        BeanUtils.copyProperties(byId, xmyCoursMainSearchDto);
        List<EsContent> esContents = new ArrayList<>();

        if (StringUtils.isNotBlank(pid)) {
            XmyCoursBodyArticParag parag = xmyCoursBodyArticParagService.getById(pid);
            if (parag != null) {
                esContents.add(new EsContent() {{
                    setId(pid);
                    setContent(parag.getContent());
                }});
            }
            xmyCoursMainSearchDto.setContent(esContents);
        } else {
            //        查询下面的文章
            List<String> articleIds = ListUtils.emptyIfNull(xmyCoursBodyArticService.list(new LambdaQueryWrapper<XmyCoursBodyArtic>()
                    .eq(XmyCoursBodyArtic::getCoursewareId, id)).stream().map(m -> m.getArticleId()).collect(Collectors.toList()));
//        查询文章的段落
            if (articleIds == null || articleIds.size() == 0) {
                articleIds.add("-1");
            }
            List<XmyCoursBodyArticParag> list = xmyCoursBodyArticParagService.list(new LambdaQueryWrapper<XmyCoursBodyArticParag>().in(XmyCoursBodyArticParag::getArticleId, articleIds));

            list.forEach(m -> {
                if (StringUtils.isNotBlank(m.getContent())) {
                    esContents.add(new EsContent() {{
                        setId(m.getParagraphId());
                        setContent(m.getContent());
                    }});
                    xmyCoursMainSearchDto.setContent(esContents);
                }
            });
        }

        Boolean aBoolean = esUtils.saveOne(coursName, xmyCoursMainSearchDto);

    }

    @Override
    public List<XmyCoursMainSearchDto> search(String text) {
        List<XmyCoursMainSearchDto> search = esUtils.search(new XmyCoursMainSearchDto(), coursName, text, fields);
        return search;
    }


    @Override
    public String updateAll(String id) {
        UpdateByQueryRequest request = new UpdateByQueryRequest(coursName);
        request.setQuery(new TermQueryBuilder("coursewareId", id));
        XmyCoursMain byId = xmyCoursMainService.getById(id);
        if (byId == null) {
            return "未找到课件主题";
        }
//        ctx._source.["品牌"]
        StringBuilder sb = new StringBuilder();
        request.setScript(new Script(
                        ScriptType.INLINE,
                        "painless",
                        "ctx._source.contentSonType='" + byId.getContentSonType() +
                                "';ctx._source.coursewareName='" + byId.getCoursewareName() +
                                "';ctx._source.templateType='" + byId.getTemplateType() +
                                "';ctx._source.auditStatus='" + byId.getAuditStatus() +
                                "';ctx._source.coverImage='" + byId.getCoverImage() + "';"
                        , Collections.emptyMap()
                )
        );
//        request.setScript(new Script("ctx._source['coursewareName']=测试;"));
        return esUtils.updateAll(request);
    }

    @Override
    public String update(String id, String pid) {
        String esId = esUtils.getEsIdbyContentId(coursName, id, "coursewareId", pid);
        if (org.apache.commons.lang3.StringUtils.isBlank(esId)) {
            log.info("更新时未找到es内容,id为:{}", id);
            return "更新时未找到es内容";
        }
//        根据id查询内容
        XmyCoursMain byId = xmyCoursMainService.getById(id);
        UpdateRequest updateRequest = new UpdateRequest(coursName, esId);
        XmyCoursMainSearchDto vo = new XmyCoursMainSearchDto();
        BeanUtils.copyProperties(byId, vo);
//        查询下面的文章
//        List<String> articleIds = ListUtils.emptyIfNull(xmyCoursBodyArticService.list(new LambdaQueryWrapper<XmyCoursBodyArtic>()
//                .eq(XmyCoursBodyArtic::getCoursewareId, id)).stream().map(m -> m.getArticleId()).collect(Collectors.toList()));
//        查询文章的段落
//        if (articleIds == null || articleIds.size() == 0) {
//            articleIds.add("-1");
//        }
        if (StringUtils.isNotBlank(pid)) {
            XmyCoursBodyArticParag parag = xmyCoursBodyArticParagService.getById(pid);
            List<EsContent> esContents = new ArrayList<>();
            esContents.add(new EsContent() {{
                setId(parag.getParagraphId());
                setContent(parag.getContent());
            }});
            vo.setContent(esContents);
        }
        updateRequest.doc(JSON.toJSONString(vo), XContentType.JSON);
        return esUtils.update(updateRequest);
    }

    @Override
    public void delete(String id, String pid) {
        if (StringUtils.isNotBlank(pid)) {
            String esIdbyContentId = esUtils.getEsIdbyContentId(coursName, id, "coursewareId", pid);
            esUtils.delete(coursName, esIdbyContentId);
        } else {
            esUtils.deleteByIds(coursName, id, "coursewareId");
        }
    }

    @Override
    public ResScrollerDto scroll(ReqScrollerDto dto) {
        ResScrollerDto scrollers = esUtils.scrollers(dto.getText(), dto.getSid(), dto.getSize(), coursName, fields);
        return scrollers;
    }

    @Override
    public void saveAll() {
//        查询出全部的课程
        List<XmyCoursMain> mains = xmyCoursMainService.list(new LambdaQueryWrapper<XmyCoursMain>()
                .eq(XmyCoursMain::getUseFlag, "1"));
        for (int i = 0; i < mains.size(); i++) {
            log.info("开始存入第:{}", i);
            XmyCoursMain byId = mains.get(i);
            XmyCoursMainSearchDto xmyCoursMainSearchDto = new XmyCoursMainSearchDto();
            BeanUtils.copyProperties(byId, xmyCoursMainSearchDto);
            List<EsContent> esContents = new ArrayList<>();


            //        查询下面的文章
            List<String> articleIds = ListUtils.emptyIfNull(xmyCoursBodyArticService.list(new LambdaQueryWrapper<XmyCoursBodyArtic>()
                    .eq(XmyCoursBodyArtic::getCoursewareId, byId.getCoursewareId())).stream().map(m -> m.getArticleId()).collect(Collectors.toList()));
//        查询文章的段落
            if (articleIds == null || articleIds.size() == 0) {
                articleIds.add("-1");
            }
            List<XmyCoursBodyArticParag> list = xmyCoursBodyArticParagService.list(new LambdaQueryWrapper<XmyCoursBodyArticParag>().in(XmyCoursBodyArticParag::getArticleId, articleIds));

            list.forEach(m -> {
                if (StringUtils.isNotBlank(m.getContent())) {
                    esContents.add(new EsContent() {{
                        setId(m.getParagraphId());
                        setContent(m.getContent());
                    }});
                    xmyCoursMainSearchDto.setContent(esContents);
                }
            });
            Boolean aBoolean = esUtils.saveOne(coursName, xmyCoursMainSearchDto);
            log.info("第:{} 存入结果:{}", i, aBoolean);
        }

    }
}
