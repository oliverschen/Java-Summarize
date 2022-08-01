package com.github.oliverschen.service;

import com.alibaba.fastjson.JSON;
import com.github.oliverschen.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static com.github.oliverschen.constant.CommonEnum.*;
import static org.elasticsearch.action.update.UpdateHelper.ContextFields.INDEX;

@Slf4j
@Service
public class EsService {

    @Autowired
    private RestHighLevelClient esRestHighLevelClient;


    /**
     * 创建索引
     *
     * @param index index 名称
     */
    public DocWriteResponse.Result createIndex(String index, String id, Map<String, Object> source) {
        IndexRequest request = new IndexRequest(index)
                .id(id)
                .source(source);
        try {
            IndexResponse response = esRestHighLevelClient.index(request, RequestOptions.DEFAULT);
            return response.getResult();
         } catch (IOException e) {
            log.error("EsService.createIndex error:", e);
            throw new ServiceException(INDEX_CREATE_ERROR);
        }
    }

    /**
     * 创建索引
     * @param index 索引名
     */
    public boolean createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            CreateIndexResponse response = esRestHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (IOException e) {
            log.error("EsService.createIndex error:", e);
            throw new ServiceException(INDEX_CREATE_ERROR);
        }
    }


    /**
     * 创建 document
     *
     * @param id     entity id
     * @param entity 实体
     * @param <T>    泛型
     */
    public <T> DocWriteResponse.Result createDocument(String id, Class<T> entity) {
        if (Objects.isNull(entity)) {
            throw new ServiceException(DOCUMENT_CREATE_ENTITY_NOT_NULL_ERROR);
        }
        IndexRequest request = new IndexRequest(INDEX)
                .id(id)
                .source(JSON.toJSONString(entity), XContentType.JSON);
        try {
            IndexResponse response = esRestHighLevelClient.index(request, RequestOptions.DEFAULT);
            return response.getResult();
        } catch (IOException e) {
            log.error("EsService.createDocument error:", e);
            throw new ServiceException(DOCUMENT_CREATE_ERROR);
        }
    }

}
