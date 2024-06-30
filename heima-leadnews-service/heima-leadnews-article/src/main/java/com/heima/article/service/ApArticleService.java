package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {
    /**
     * 根据参数加载文章列表
     * @param dto
     * @param type 1 加载更多 2加载最新
     * @return
     */
    public ResponseResult load(ArticleHomeDto dto,Short type);

    /**
     * 保存app端相关文章
     * @param dto
     * @return
     */
    ResponseResult saveArticle(ArticleDto dto);
    /**
     * 加载文章列表
     * @param dto
     * @param type  1 加载更多   2 加载最新
     * @param firstPage  true  是首页  flase 非首页
     * @return
     */
    public ResponseResult load2(ArticleHomeDto dto,Short type,boolean firstPage);

    /**
     * 同步数据
     */
    void syncData();
}
