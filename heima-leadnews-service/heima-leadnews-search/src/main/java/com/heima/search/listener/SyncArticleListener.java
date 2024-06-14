package com.heima.search.listener;

import com.alibaba.fastjson.JSON;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.search.vos.SearchArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@Slf4j
public class SyncArticleListener {
    @Resource
    private RestHighLevelClient client;

    @KafkaListener(topics = ArticleConstants.ARTICLE_ES_SYNC_TOPIC)
    public void onMessage(String message){
        log.info("SyncArticleListener,message={}",message);

        SearchArticleVo vo = JSON.parseObject(message, SearchArticleVo.class);
        IndexRequest request = new IndexRequest("app_info_article");
        request.id(vo.getId().toString());
        request.source(message, XContentType.JSON);

        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("sync es error={}",e);
        }
    }
}
