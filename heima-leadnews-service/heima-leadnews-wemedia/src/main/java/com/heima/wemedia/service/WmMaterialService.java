package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 查询图片
     * @param wmMaterialDto
     * @return
     */
    ResponseResult findList(WmMaterialDto wmMaterialDto);

    /**
     * 根据id删除素材
     * @param id
     * @return
     */
    ResponseResult delPicture(Long id);

    /**
     * 收藏素材
     * @param id
     * @return
     */

    ResponseResult collect(Long id);

    ResponseResult cancelCollect(Long id);
}