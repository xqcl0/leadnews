package com.heima.article.job;

import com.heima.article.service.ApArticleService;
import com.heima.article.service.HotArticleService;
import com.heima.common.redis.CacheService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComputeHotArticleJob {

    @Autowired
    private HotArticleService hotArticleService;
    @Autowired
    private ApArticleService apArticleService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @XxlJob("computeHotArticleJob")
    public void handle() {
        log.info("热文章分值计算调度任务开始执行...");
        hotArticleService.computeHotArticle();
        log.info("热文章分值计算调度任务结束...");

    }

    @XxlJob("syncDataJob")
    public void handleSyncData() {
        log.info("同步redis用户行为数据至MySQL...");
        apArticleService.syncData();
        log.info("同步用户行为数据结束...");
    }
}