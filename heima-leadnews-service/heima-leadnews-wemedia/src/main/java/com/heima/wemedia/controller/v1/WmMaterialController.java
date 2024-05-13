package com.heima.wemedia.controller.v1;

import com.baomidou.mybatisplus.extension.api.R;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/del_picture/{id}")
    public ResponseResult delPicture(@PathVariable("id") Long id){
        log.info("删除素材id:{}",id);
        return wmMaterialService.delPicture(id);
    }
    @GetMapping("/collect/{id}")
    public ResponseResult collect(@PathVariable("id") Long id){
        log.info("收藏素材id:{}",id);
        return wmMaterialService.collect(id);
    }
    @GetMapping("/cancel_collect/{id}")
    public ResponseResult cancelCollect(@PathVariable("id") Long id){
        log.info("取消收藏素材id:{}",id);
        return wmMaterialService.cancelCollect(id);
    }


}