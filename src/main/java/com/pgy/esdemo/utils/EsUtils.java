package com.pgy.esdemo.utils;

import com.alibaba.fastjson.JSONObject;
import com.pgy.esdemo.dto.EsContent;
import com.pgy.esdemo.dto.ResScrollerDto;
import com.pgy.esdemo.dto.XmyContentMianSearchVo;
import com.pgy.esdemo.dto.XmyCoursMainSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.BulkByScrollTask;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.index.similarity.ScriptedSimilarity;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/8/24 11:28
 */
@Component
@Slf4j
public class EsUtils {
    @Autowired
    private RestHighLevelClient client;

    public Boolean createContentIk(String indexName, Map<String, Object> properties) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);
        // 执行创建请求
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        log.info("创建索引：{}，结果：acknowledged:{}", indexName, acknowledged);
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        log.info("创建索引：{}，结果：shardsAcknowledged:{}", indexName, shardsAcknowledged);
        return acknowledged && shardsAcknowledged;
    }

    public Map<String, Object> getField(String type) {
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

    public <T> Boolean saveOne(String index, T t) {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest(index)
                .source(JSONObject.toJSONString(t), XContentType.JSON));
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean b = bulkResponse.hasFailures();
        log.info("保存返回:{}", b);
        return b;
    }

    /**
     * @param text  搜索的文本内容
     * @param field 字段
     * @return
     */
    public <T> List<T> search(T t, String indexName, String text, String... field) {
        SearchRequest request = new SearchRequest(indexName);
        //构建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(matchQuery(field[0], text))
                .should(matchQuery(field[1], text));

//
        sourceBuilder.from(0).size(20);
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long value = response.getHits().getTotalHits().value;
        List list = new ArrayList<T>();
        if (value > 0) {
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                XmyCoursMainSearchDto xmyCoursMainSearchDto = JSONObject.parseObject(hit.getSourceAsString(), XmyCoursMainSearchDto.class);
                list.add(xmyCoursMainSearchDto);
            }
        }

        return list;
    }


    /**
     * 根据自己的id获得es的id
     *
     * @param indexName
     * @param contentId
     * @param idName
     * @return
     */
    public String getEsIdbyContentId(String indexName, String contentId, String idName, String pId) {
        String id = "";
        SearchRequest request = new SearchRequest(indexName);
        //构建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(matchQuery(idName, contentId));
        if (StringUtils.isNotBlank(pId)) {
            boolQueryBuilder.must(matchQuery("content.id", pId));
        }
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

    public String update(UpdateRequest updateRequest) {
        UpdateResponse update = null;
        try {
            update = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = checkResponse(update.getResult());
        log.info("更新:{}", s);
        return s;
    }

    public String updateAll(UpdateByQueryRequest updateByQueryRequest) {
        BulkByScrollResponse bulkByScrollResponse = null;
        try {
            bulkByScrollResponse = client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long updated = bulkByScrollResponse.getUpdated();
        return "" + updated;
    }

    public void delete(String indexName, String esIdbyContentId) {
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

    public void deleteByIds(String indexName, String id, String name) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
        request.setQuery(new TermQueryBuilder(name, id));
        BulkByScrollResponse resp = null;
        try {
            resp = client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String checkResponse(DocWriteResponse.Result result) {
        String res = "";
        if (result == DocWriteResponse.Result.CREATED) {
            // 文档已创建
            res = "创建";
        } else if (result == DocWriteResponse.Result.UPDATED) {
            // 文档已更新
            res = "更新";
        } else if (result == DocWriteResponse.Result.DELETED) {
            // 文档已删除
            res = "删除";
        } else if (result == DocWriteResponse.Result.NOOP) {
            // 文档不受更新的影响
            res = "未知";
        }
        return res;
    }

    public List<String> getIds(String indexName, String coursId) {
        SearchRequest request = new SearchRequest(indexName);
        //构建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(matchQuery("coursewareId", coursId));
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
        List list = new ArrayList<String>();
        if (value > 0) {
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                list.add(hit.getId());
            }
        }

        return list;
    }

    /**
     * @param text 搜索内容
     * @param sid  分页id
     * @param size 每页数量
     * @return
     */
    public ResScrollerDto scrollers(String text, String sid, Integer size, String indexName, String... field) {
        if (StringUtils.isBlank(text)) {
            text = "";
        }
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchHit[] hits = null;
        String scrollId = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(sid)) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(sid);
            scrollRequest.scroll(TimeValue.timeValueMinutes(10));
            SearchResponse searchScrollResponse = null;
            try {
                searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            } catch (Exception e) {
                log.info("分页的异常:{}", e.getMessage());
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

            boolQueryBuilder.must(
                    new BoolQueryBuilder()
                            .should(matchQuery(field[0], text).boost(999))
                            .should(matchQuery(field[1], text).boost(1))
            )
                    .must(termsQuery("auditStatus", "4"))
            ;

            HighlightBuilder highlightBuilder = new HighlightBuilder();
            //设置高亮字段
//            highlightBuilder.field("content.content");
//            highlightBuilder.field("coursewareName");

            List<HighlightBuilder.Field> fields = highlightBuilder.fields();
            fields.add(new HighlightBuilder.Field("content.content"));
            fields.add(new HighlightBuilder.Field("coursewareName"));

            //如果要多个字段高亮,这项要为false
            highlightBuilder.requireFieldMatch(false);
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");

            //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
            highlightBuilder.fragmentSize(800000); //最大高亮分片数
            highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段
            searchSourceBuilder.highlighter(highlightBuilder);
            searchSourceBuilder.query(boolQueryBuilder);
//
            searchSourceBuilder.size(size == null ? 10 : size);
            searchRequest.source(searchSourceBuilder);
//        设置游标过期时间
            searchRequest.scroll(TimeValue.timeValueMinutes(10));
            SearchResponse searchResponse = null;
            try {
                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();

            }
            scrollId = searchResponse.getScrollId();
            hits = searchResponse.getHits().getHits();
        }


        List<XmyCoursMainSearchDto> res = new ArrayList<>();
        for (SearchHit hit : hits) {
            XmyCoursMainSearchDto xmyCoursMainSearchDto = JSONObject.parseObject(hit.getSourceAsString(), XmyCoursMainSearchDto.class);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("content.content");
            if (highlightField != null) {
                Text[] fragments = highlightField.fragments();
                String t = "";
                for (Text fragment : fragments) {
                    t += fragment;
                }
                //高亮标题覆盖原标题
                List<EsContent> content = xmyCoursMainSearchDto.getContent();
                for (EsContent e : content) {
                    e.setContent(t);
                }
            }

            HighlightField courName = highlightFields.get("coursewareName");
            if (courName != null) {
                Text[] fragments = courName.fragments();
                String t = "";
                for (Text fragment : fragments) {
                    t += fragment;
                }
                //高亮标题覆盖原标题
                xmyCoursMainSearchDto.setCoursewareName(t);
            }


            res.add(xmyCoursMainSearchDto);
        }

        ResScrollerDto dto = new ResScrollerDto();
        dto.setSid(res.size() == 0 ? "" : scrollId);
        dto.setList(res);
        log.info("分页:{}", dto);
        return dto;
    }

}
