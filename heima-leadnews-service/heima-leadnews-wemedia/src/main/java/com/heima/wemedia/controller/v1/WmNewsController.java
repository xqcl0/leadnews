package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmDownOrUpDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {
    @Autowired
    private WmNewsService wmNewsService;
    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.findAll(dto);
    }
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto wmNewsDto){
        return wmNewsService.submitNews(wmNewsDto);
    }
    @GetMapping("/one/{id}")
    public ResponseResult findOne(@PathVariable Integer id){
        return wmNewsService.findOne(id);
    }
    @GetMapping("del_news/{id}")
    public ResponseResult delNews(@PathVariable Integer id){
        return wmNewsService.delNews(id);
    }
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmDownOrUpDto dto){
        return wmNewsService.downOrUp(dto);
    }
}
