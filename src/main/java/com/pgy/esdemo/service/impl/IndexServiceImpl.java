package com.pgy.esdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pgy.esdemo.dto.ReqScrollerDto;
import com.pgy.esdemo.dto.ResScrollerDto;
import com.pgy.esdemo.dto.XmyContentMianSearchVo;
import com.pgy.esdemo.entity.XmyClasSetd;
import com.pgy.esdemo.entity.XmyContentClasCenter;
import com.pgy.esdemo.entity.XmyContentClasSetd;
import com.pgy.esdemo.entity.XmyContentMian;
import com.pgy.esdemo.mapper.XmyContentMianMapper;
import com.pgy.esdemo.service.*;
import com.pgy.esdemo.utils.EsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/5/12 10:40
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IXmyClasSetdService xmyClasSetdService;
    @Autowired
    private IXmyContentClasSetdService xmyContentClasSetdService;
    @Autowired
    private IXmyContentClasCenterService xmyContentClasCenterService;
    @Autowired
    private IXmyContentMianService xmyContentMianService;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private XmyContentMianMapper xmyContentMianMapper;
    @Value("${elasticsearch.index-name}")
    private String indexName;
    @Autowired
    private EsUtils esUtils;

    //    ??????
    private String[] fields = new String[]{"contentName", "contentIntroduction", "content"};

    @Override
    public void saveToes() {
        LambdaQueryWrapper<XmyClasSetd> xmyClasSetdLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //        ????????????????????????????????????
//        1. ???????????? ?????????????????????id
        List<Long> conclassIds = ListUtils.emptyIfNull(xmyClasSetdService
                .list(xmyClasSetdLambdaQueryWrapper
//          classify_auto    ????????????(1 DETT???????????????2 ?????????????????? 3 ?????????)
//                                .eq(XmyClasSetd::getClassifyAuto, "1")
                ))
                .stream().map(m -> m.getClassifyId())
                .collect(Collectors.toList());
        if (conclassIds.size() == 0) {
            conclassIds.add(Long.valueOf(-1));
        }
//        2. ????????????id???????????????id??????
        List<String> contentIds = ListUtils.emptyIfNull(xmyContentClasCenterService
                .list(new LambdaQueryWrapper<XmyContentClasCenter>()
                        .in(XmyContentClasCenter::getClassifyId, conclassIds))
                .stream().map(m -> m.getContentId()).collect(Collectors.toList()));
        if (contentIds.size() == 0) {
            contentIds.add("-1");
        }
//        3. ???????????????????????????
        List<XmyContentMianSearchVo> contentModelList = xmyContentMianService.listContentModel(contentIds);

//        4. ????????????id ????????????

        contentModelList = contentSave(contentModelList);

        log.info("{}", contentModelList);

//        ??????es
        for (int i = 0; i < contentModelList.size(); i++) {
            log.info("???????????????{}????????????{}", i, contentModelList.size());
            save(contentModelList.get(i));
        }

    }

    public List<XmyContentMianSearchVo> contentSave(List<XmyContentMianSearchVo> contentModelList) {
        for (XmyContentMianSearchVo m : contentModelList) {
//            ????????????????????? ??????????????????(1?????? 2??????)
            List<String> content = new ArrayList<>();
            if (org.apache.commons.lang3.StringUtils.equals(m.getContentSonType(), "1")) {
//                ???????????????????????????????????????
                List<String> conIds = ListUtils.emptyIfNull(xmyContentMianMapper.selectConIds(m.getContentId()));
//                ????????????id??????
                if (conIds.size() != 0) {
                    content = xmyContentMianMapper.getPara(m.getContentId(), m.getContentSonType(), conIds);
                }
            } else {
                content = xmyContentMianMapper.getPara(m.getContentId(), m.getContentSonType(), null);
            }
            String join = StringUtils.join(content, ",");
            m.setContent(join);
        }

        return contentModelList;
    }

    @Override
    public List<XmyContentMianSearchVo> search(String text) {
        List<XmyContentMianSearchVo> res = new ArrayList<>();
//        ???????????? ??????
        List<XmyContentMianSearchVo> contentNameAndDes = searchES(text, false, fields);

        List<XmyContentMianSearchVo> contents = searchES(text, true, fields);
        res.addAll(contentNameAndDes);
        res.addAll(contents);

//        ???????????????
        log.info("??????:{}", res.size());
        return res;
    }

    @Override
    public void createIndex() {
        try {
            createContentIk();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String update(String id) {
        String esIdbyContentId = getEsIdbyContentId(id);
        if (org.apache.commons.lang3.StringUtils.isBlank(esIdbyContentId)) {
            log.info("??????????????????es??????,id???:{}", id);
            return "??????????????????es??????";
        }
//        ??????id????????????
        XmyContentMian m = xmyContentMianMapper.selectById(id);
        UpdateRequest updateRequest = new UpdateRequest(indexName, esIdbyContentId);
        XmyContentMianSearchVo vo = new XmyContentMianSearchVo();
        BeanUtils.copyProperties(m, vo);

//        ????????????
        List<String> content = new ArrayList<>();
        if (org.apache.commons.lang3.StringUtils.equals(m.getContentSonType(), "1")) {
//                ???????????????????????????????????????
            List<String> conIds = ListUtils.emptyIfNull(xmyContentMianMapper.selectConIds(m.getContentId()));
//                ????????????id??????
            if (conIds.size() != 0) {
                content = xmyContentMianMapper.getPara(m.getContentId(), m.getContentSonType(), conIds);
                log.info("content:{}", content);
            }
        } else {
            content = xmyContentMianMapper.getPara(m.getContentId(), m.getContentSonType(), null);
        }
        String join = StringUtils.join(content, ",");
        vo.setContent(join);
        log.info("??????:{}", vo);
        if (StringUtils.isBlank(vo.getContentIntroduction())) {
            vo.setContentIntroduction("");
        }
        if (StringUtils.isBlank(vo.getContent())) {
            vo.setContent("");
        }
        if (StringUtils.isBlank(vo.getModleName())) {
            vo.setContent("");
        }
        updateRequest.doc(JSON.toJSONString(vo), XContentType.JSON);
        UpdateResponse update = null;
        try {
            update = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = checkResponse(update.getResult());
        log.info(s);
        return s;
    }

    @Override
    public void delete(String id) {
        String esIdbyContentId = getEsIdbyContentId(id);
        DeleteRequest deleteRequest = new DeleteRequest(indexName, esIdbyContentId);
        DeleteResponse delete = null;
        try {
            delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DocWriteResponse.Result result = delete.getResult();
        String s = checkResponse(result);
        log.info(s);
    }

    public String checkResponse(DocWriteResponse.Result result) {
        String res = "";
        if (result == DocWriteResponse.Result.CREATED) {
            // ???????????????
            res = "??????";
        } else if (result == DocWriteResponse.Result.UPDATED) {
            // ???????????????
            res = "??????";
        } else if (result == DocWriteResponse.Result.DELETED) {
            // ???????????????
            res = "??????";
        } else if (result == DocWriteResponse.Result.NOOP) {
            // ???????????????????????????
            res = "??????";
        }
        return res;
    }


    public String getEsIdbyContentId(String contentId) {
        String id = "";
        SearchRequest request = new SearchRequest(indexName);
        //????????????
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(matchQuery("contentId", contentId));
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long value = response.getHits().getTotalHits().value;
        SearchHit[] hits = response.getHits().getHits();

        for (SearchHit hit : hits) {
            id = hit.getId();
        }
        return id;
    }


    /**
     * @param text  ?????????????????????
     * @param flag  ????????? ??????????????? true ??????
     * @param field ??????
     * @return
     */
    public List<XmyContentMianSearchVo> searchES(String text, Boolean flag, String... field) {
        SearchRequest request = new SearchRequest(indexName);
        //????????????
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (flag) {
            boolQueryBuilder.should(matchQuery(field[2], text))
                    .should(matchPhraseQuery(field[2], text));
        } else {
            boolQueryBuilder.should(matchQuery(field[0], text))
                    .should(matchQuery(field[1], text));
        }


//
        sourceBuilder.from(0).size(50000);
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long value = response.getHits().getTotalHits().value;
        List<XmyContentMianSearchVo> res = new ArrayList<>();
        if (value > 0) {
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                XmyContentMianSearchVo xmyContentMianSearchVo = JSONObject.parseObject(hit.getSourceAsString(), XmyContentMianSearchVo.class);
                res.add(xmyContentMianSearchVo);
            }
        }
        return res;
    }

    @Override
    public ResScrollerDto scroller(ReqScrollerDto dto) {
//        ????????????type ??????????????????????????????????????????
        if (StringUtils.isNotBlank(dto.getType())) {
            List<String> strings = get(dto.getType());
            if (StringUtils.equals(dto.getType(), "9")) {
                dto.setSize(12);
            }
            return scrollers(dto.getText(), dto.getSid(), dto.getSize() == null ? 10 : dto.getSize(), dto.getType(), strings, fields);
        }

//        ???????????? ?????????????????????????????????
        List<String> dett = get("1");
        List<String> cloud = get("2");

        ResScrollerDto dettScroller = scrollers(dto.getText(), null, 10, "1", dett, fields);
        ResScrollerDto cloudScroller = scrollers(dto.getText(), null, 10, "2", cloud, fields);
        ResScrollerDto res = new ResScrollerDto();
        res.setDettContentList(dettScroller.getDettContentList());
        res.setCloudContentList(cloudScroller.getCloudContentList());
        return res;
    }

    @Override
    public Boolean saveOne(String id) {
        List<String> contentIds = Arrays.asList(id);
        //???????????????????????????
        List<XmyContentMianSearchVo> contentModelList = xmyContentMianService.listContentModel(contentIds);

        // ????????????id ????????????

        contentModelList = contentSave(contentModelList);
//        for (XmyContentMianSearchVo m : contentModelList) {
//            List<String> content = xmyContentMianMapper.getPara(m.getContentId(), m.getContentSonType(), null);
//            String join = StringUtils.join(content, ",");
//            m.setContent(join);
//        }

        log.info("???????????????{}", contentModelList);
        Boolean flag = false;
//        ??????es
        for (int i = 0; i < contentModelList.size(); i++) {
            log.info("???????????????{}????????????{}", i, contentModelList.size());
            flag = save(contentModelList.get(i));
        }
        return flag;
    }

    /**
     * @param type 1 ???????????? 2 ???????????????
     */
    public List<String> get(String type) {
        LambdaQueryWrapper<XmyClasSetd> wrapper = new LambdaQueryWrapper<>();
        // 1. ???????????? ????????????????????????id
        if (!StringUtils.equals(type, "9")) {
            wrapper.eq(XmyClasSetd::getClassifyAuto, type)
                    .eq(XmyClasSetd::getClassifyType, "2");
        }
        List<Long> conclassIds = ListUtils.emptyIfNull(xmyClasSetdService
                .list(wrapper))
                .stream().map(m -> m.getClassifyId())
                .collect(Collectors.toList());
        if (conclassIds.size() == 0) {
            conclassIds.add(Long.valueOf(-1));
        }
//        2. ????????????id???????????????id??????
        List<String> contentIds = ListUtils.emptyIfNull(xmyContentClasCenterService.list(
                new LambdaQueryWrapper<XmyContentClasCenter>()
                        .in(XmyContentClasCenter::getClassifyId, conclassIds)
        )
                .stream()
                .map(m -> m.getContentId())
                .collect(Collectors.toList()));
        if (contentIds.size() == 0) {
            contentIds.add("-1");
        }
        return contentIds;
    }


    /**
     * @param text       ????????????
     * @param sid        ??????id
     * @param size       ????????????
     * @param type       ????????????
     * @param contentIds ?????????id??????
     * @param field      ????????????
     * @return
     */
    public ResScrollerDto scrollers(String text, String sid, Integer size, String type, List<String> contentIds, String... field) {
        if (StringUtils.isBlank(text)) {
            text = "";
        }
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchHit[] hits = null;
        String scrollId = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(sid)) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(sid);
            scrollRequest.scroll(TimeValue.timeValueMinutes(5));
            SearchResponse searchScrollResponse = null;
            try {
                searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            } catch (Exception e) {
                log.info("???????????????:{}", e.getMessage());
//                e.printStackTrace();
                ResScrollerDto dto = new ResScrollerDto();
                dto.setFlush(true);
                dto.setSid("");
                return dto;
            }
            scrollId = searchScrollResponse.getScrollId();
            hits = searchScrollResponse.getHits().getHits();

        } else {

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

            if (StringUtils.equals(type, "9")) {
                boolQueryBuilder.should(matchQuery(field[0], text))
                        .should(matchQuery(field[1], text))
                        .should(matchQuery(field[2], text))
                        .should(matchPhraseQuery(field[2], text))
                ;

            } else {
                boolQueryBuilder.should(matchQuery(field[0], text))
                        .should(matchQuery(field[1], text))
                        .should(matchQuery(field[2], text))
                        .should(matchPhraseQuery(field[2], text))
                        .must(termsQuery("contentId", contentIds))
                ;
            }

            searchSourceBuilder.query(boolQueryBuilder);
//
            searchSourceBuilder.size(size);
            searchRequest.source(searchSourceBuilder);
//        ????????????????????????
            searchRequest.scroll(TimeValue.timeValueMinutes(5));
            SearchResponse searchResponse = null;
            try {
                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();

            }
            scrollId = searchResponse.getScrollId();
            hits = searchResponse.getHits().getHits();
        }


        List<XmyContentMianSearchVo> res = new ArrayList<>();
        for (SearchHit hit : hits) {
            XmyContentMianSearchVo xmyContentMianSearchVo = JSONObject.parseObject(hit.getSourceAsString(), XmyContentMianSearchVo.class);
            res.add(xmyContentMianSearchVo);
        }

        ResScrollerDto dto = new ResScrollerDto();
        dto.setSid(res.size() == 0 ? "" : scrollId);
//        dto.setRes(res);
        if (StringUtils.equals(type, "1")) {
            dto.setDettContentList(res);
        } else if (StringUtils.equals(type, "2")) {
            dto.setCloudContentList(res);
        } else {
            dto.setDettContentList(res);
        }
        log.info("??????:{}", dto);
        return dto;
    }

    public Boolean save(XmyContentMianSearchVo vo) {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest(indexName)
                .source(JSONObject.toJSONString(vo), XContentType.JSON));
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean b = bulkResponse.hasFailures();
        log.info("????????????:{}", b);
        return b;
    }


    public void createContentIk() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        Map<String, Object> properties = new HashMap<>();
        properties.put("contentId", getField("keyword"));
        properties.put("contentName", getField("text"));
        properties.put("contentIntroduction", getField("text"));
        properties.put("coverImage", getField("keyword"));
        properties.put("contentSonType", getField("keyword"));
        properties.put("pageTemplateType", getField("keyword"));
        properties.put("sheetStatus", getField("keyword"));
        properties.put("templateId", getField("keyword"));
        properties.put("modleName", getField("keyword"));
        properties.put("content", getField("text"));
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);
        // ??????????????????
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        log.info("acknowledged:{}", acknowledged);
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        log.info("shardsAcknowledged:{}", shardsAcknowledged);
    }

    private static Map<String, Object> getField(String type) {
        Map<String, Object> result = new HashMap<>();
        result.put("type", type);
        if ("text".equals(type)) {
            result.put("analyzer", "ik_max_word");
            result.put("search_analyzer", "ik_max_word");
//            result.put("ignore_above", "");
            result.put("term_vector", "with_positions_offsets");
        }

        return result;
    }
}
