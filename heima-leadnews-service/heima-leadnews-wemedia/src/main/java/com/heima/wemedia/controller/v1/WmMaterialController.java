package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
@Slf4j
public class WmMaterialController {
    @Autowired
    WmMaterialService wmMaterialService;

    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        log.info("上传图片{}",multipartFile);
        return wmMaterialService.uploadPicture(multipartFile);
    }
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto wmMaterialDto){
        log.info("查询list:{}",wmMaterialDto);
        return wmMaterialService.findList(wmMaterialDto);
    }


}