package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {
    // 单页最大加载的数字
    private final static short MAX_PAGE_SIZE = 50;
    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 根据参数加载文章列表
     *
     * @param dto
     * @param type 1 加载更多 2加载最新
     * @return
     */
    @Override
    public ResponseResult load(ArticleHomeDto dto, Short type) {
        // 1 判断参数
        //1.校验参数
        Integer size = dto.getSize();
        if (size == null || size == 0) {
            size = 10;
        }
        size = Math.min(size, MAX_PAGE_SIZE);
        dto.setSize(size);

        //类型参数检验
        if (!type.equals(ArticleConstants.LOADTYPE_LOAD_MORE) && !type.equals(ArticleConstants.LOADTYPE_LOAD_NEW)) {
            type = ArticleConstants.LOADTYPE_LOAD_MORE;
        }
        //文章频道校验
        if (StringUtils.isEmpty(dto.getTag())) {
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }

        //时间校验
        if (dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if (dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());

        // 2 查询
        List<ApArticle> articleList = apArticleMapper.loadArticleList(dto, type);

        return ResponseResult.okResult(articleList);
    }
}
