package com.example.eventbus.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.eventbus.demo.service.IpSqlMsgService;
import com.example.eventbus.demo.service.model.IpSqlMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @name: task_event
 * @description:
 * @author: zhxy
 * @create: 2022/8/14 9:39
 **/
@Service
@Slf4j
public class IpSqlServiceImpl implements IpSqlMsgService {

    @Autowired
    @Qualifier("restHighLevelClient")
    public RestHighLevelClient client;

    private static  final  String INDEX = "ip_sql_msg";

    @Override
    public String createIndexSqlMsg() {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
        );
        createIndexRequest.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"ip\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"sex\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"name\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"id\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"age\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("[createIndexSqlMsg][error][{}]", ExceptionUtils.getStackTrace(e));
        }
        return JSON.toJSONString(createIndexResponse);

    }

    @Override
    public String saveIpSqlMsgDoc(IpSqlMsgDto ipSqlMsgDto) {

        UUID uuid = UUID.randomUUID();
        ipSqlMsgDto.setId(uuid.toString());
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .id(ipSqlMsgDto.getId())
                .source(JSON.toJSONString(ipSqlMsgDto), XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("[saveIpSqlMsgDoc][error][{}]", ExceptionUtils.getStackTrace(e));
            return "error";
        }catch (Exception e){
            log.error("[saveIpSqlMsgDoc][error][{}]", ExceptionUtils.getStackTrace(e));
            return "error";
        }
        return indexResponse.status().toString();

    }

    @Override
    public String bulkSaveIpSqlMsgDoc(List<IpSqlMsgDto> ipSqlMsgDto) {
        BulkRequest bulkRequest = new BulkRequest();
        for (IpSqlMsgDto document : ipSqlMsgDto) {
            String id = UUID.randomUUID().toString();
            document.setId(id);
            IndexRequest indexRequest = new IndexRequest(INDEX)
                    .id(id)
                    .source(JSON.toJSONString(document), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("[bulkSaveIpSqlMsgDoc][error][{}]", ExceptionUtils.getStackTrace(e));
            return "error";
        }catch (Exception e){
            log.error("[bulkSaveIpSqlMsgDoc][error][{}]", ExceptionUtils.getStackTrace(e));
            return "error";
        }
        return bulkResponse.status().toString();
    }
}
