package com.pgy.esdemo.controller;

import com.pgy.esdemo.dto.*;
import com.pgy.esdemo.service.CousrService;
import com.pgy.esdemo.service.IXmyCoursBodyArticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/8/24 11:24
 */
@RestController
@RequestMapping("/cours")
@Api(tags = "课件es")
public class CousrController {
    @Autowired
    private CousrService cousrService;


    @PostMapping("/createCours")
    @ApiOperation(value = "创建课件索引", notes = "kevin")
    public void createCours() {
        cousrService.createCours();
    }


    @PostMapping("/saveOne")
    @ApiOperation(value = "保存一个", notes = "kevin")
    public void saveOne(@RequestBody ReqIdDto dto) {
        cousrService.saveOne(dto.getId(),dto.getPid());
    }

    @PostMapping("/search")
    @ApiOperation(value = "搜索", notes = "kevin")
    public List<XmyCoursMainSearchDto> search(@RequestBody QueryDto dto) {
        return cousrService.search(dto.getText());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "tangfan")
    public String update(@RequestBody ReqIdDto dto) {
        return cousrService.update(dto.getId(), dto.getPid());
    }

    @PostMapping("/updateAll")
    @ApiOperation(value = "批量更新", notes = "tangfan")
    public String updateAll(@RequestBody ReqIdDto dto) {
        return cousrService.updateAll(dto.getId());
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "kevin")
    public void delete(@RequestBody ReqIdDto dto) {
//        indexService.delete(dto.getId());
        cousrService.delete(dto.getId(), dto.getPid());
    }

    @PostMapping("/scroll")
    @ApiOperation(value = "分页", notes = "kevin")
    public ResScrollerDto scroll(@RequestBody ReqScrollerDto dto) {
        return cousrService.scroll(dto);
    }


    @PostMapping("/saveAll")
    @ApiOperation(value = "",notes = "kevin")
    public void saveAll(){
        cousrService.saveAll();
    }
}
