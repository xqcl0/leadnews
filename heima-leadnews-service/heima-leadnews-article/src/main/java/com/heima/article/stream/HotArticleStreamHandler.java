package com.heima.article.stream;

import com.alibaba.fastjson.JSON;
import com.heima.common.constants.HotArticleConstants;
import com.heima.model.mess.ArticleVisitStreamMess;
import com.heima.model.mess.UpdateArticleMess;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.checkerframework.checker.units.qual.K;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class HotArticleStreamHandler {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC);
        stream.map((key, value) -> {
                    // key设置为文章id value -> likes:1
                    UpdateArticleMess mess = JSON.parseObject(value, UpdateArticleMess.class);
                    String newValue = mess.getType().name() + ":" + mess.getAdd();
                    return new KeyValue<>(mess.getArticleId().toString(), newValue);
                })
                //根据文章聚合
                .groupBy((key, value) -> key)
                .windowedBy(TimeWindows.of(Duration.ofMinutes(10)))
                /**
                 * 自行地完成聚合的计算
                 */
                .aggregate(new Initializer<String>() {
                    /**
                     * 初始方法，返回值是消息的value
                     *
                     * @return
                     */
                    @Override
                    public String apply() {
                        return "COLLECTION:0,COMMENT:0,LIKES:0,VIEWS:0";
                    }
                    /**
                     * 真正的聚合操作，返回值是消息的value
                     */
                }, new Aggregator<String, String, String>() {
                    @Override
                    public String apply(String key, String value, String aggValue) {
                        if (StringUtils.isBlank(value)) {
                            return aggValue;
                        }
                        String[] aggAry = aggValue.split(",");
                        int col = 0, com = 0, lik = 0, vie = 0;
                        for (String agg : aggAry) {
                            String[] split = agg.split(":");
                            /**
                             * 获得初始值，也是时间窗口内计算之后的值
                             */
                            switch (UpdateArticleMess.UpdateArticleType.valueOf(split[0])) {
                                case COLLECTION:
                                    col = Integer.parseInt(split[1]);
                                    break;
                                case COMMENT:
                                    com = Integer.parseInt(split[1]);
                                    break;
                                case LIKES:
                                    lik = Integer.parseInt(split[1]);
                                    break;
                                case VIEWS:
                                    vie = Integer.parseInt(split[1]);
                                    break;
                            }
                        }
                        /**
                         * 累加操作
                         */
                        String[] valAry = value.split(":");
                        switch (UpdateArticleMess.UpdateArticleType.valueOf(valAry[0])) {
                            case COLLECTION:
                                col += Integer.parseInt(valAry[1]);
                                break;
                            case COMMENT:
                                com += Integer.parseInt(valAry[1]);
                                break;
                            case LIKES:
                                lik += Integer.parseInt(valAry[1]);
                                break;
                            case VIEWS:
                                vie += Integer.parseInt(valAry[1]);
                                break;
                        }
                        String formatStr = String.format("COLLECTION:%d,COMMENT:%d,LIKES:%d,VIEWS:%d", col, com, lik, vie);
                        System.out.println("文章的id:" + key);
                        System.out.println("当前时间窗口内的消息处理结果：" + formatStr);
                        return formatStr;

                    }
                }, Materialized.as("hot-atricle-stream-count-001"))
                .toStream()
                .map((key, value) -> {
                    return new KeyValue<>(key.key().toString(), formatObj(key.key().toString(), value));
                })
                .to(HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_TOPIC);
        return stream;
    }

    /**
     * 格式化消息的value数据
     *
     * @param articleId
     * @param value
     * @return
     */
    public String formatObj(String articleId, String value) {
        ArticleVisitStreamMess mess = new ArticleVisitStreamMess();
        mess.setArticleId(Long.valueOf(articleId));
        //COLLECTION:0,COMMENT:0,LIKES:0,VIEWS:0
        String[] valAry = value.split(",");
        for (String val : valAry) {
            String[] split = val.split(":");
            switch (UpdateArticleMess.UpdateArticleType.valueOf(split[0])) {
                case COLLECTION:
                    mess.setCollect(Integer.parseInt(split[1]));
                    break;
                case COMMENT:
                    mess.setComment(Integer.parseInt(split[1]));
                    break;
                case LIKES:
                    mess.setLike(Integer.parseInt(split[1]));
                    break;
                case VIEWS:
                    mess.setView(Integer.parseInt(split[1]));
                    break;
            }
        }
        log.info("聚合消息处理之后的结果为:{}", JSON.toJSONString(mess));
        return JSON.toJSONString(mess);

    }

}