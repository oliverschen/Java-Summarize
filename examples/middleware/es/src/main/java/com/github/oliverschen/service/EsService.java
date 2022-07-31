package com.github.oliverschen.service;

import com.github.oliverschen.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.github.oliverschen.constant.CommonEnum.INDEX_CREATE_ERROR;

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
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(source);
        try {
            IndexResponse response = esRestHighLevelClient.index(request, RequestOptions.DEFAULT);
            return response.getResult();
         } catch (IOException e) {
            log.error("EsService.createIndex error:", e);
            throw new ServiceException(INDEX_CREATE_ERROR);
        }
    }

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


}
