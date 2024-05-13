package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmDownOrUpDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {

    public ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     * 发布文章或保存草稿
     * @param wmNewsDto
     * @return
     */
    ResponseResult submitNews(WmNewsDto wmNewsDto);

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    ResponseResult findOne(Integer id);

    /**
     * 删除文章
     * @param id
     * @return
     */
    ResponseResult delNews(Integer id);

    /**
     * 上架或下架
     * @param dto
     * @return
     */
    ResponseResult downOrUp(WmDownOrUpDto dto);
}
