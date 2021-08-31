package com.pgy.esdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pgy.esdemo.dto.*;
import com.pgy.esdemo.mapper.XmyClasSetdMapper;
import com.pgy.esdemo.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/5/11 15:28
 */
@RestController
@RequestMapping("/index")
@Api(tags = "es")
public class IndexController {


    @Autowired
    private IndexService indexService;
    @Autowired
    private XmyClasSetdMapper xmyClasSetdMapper;



    @PostMapping("/saveToes")
    @ApiOperation(value = "将数据库存入es", notes = "kevin")
    public void saveToEs() {
        indexService.saveToes();
    }


    @PostMapping("/saveOne")
    @ApiOperation(value = "通过内容id,保存单个数据", notes = "kevin")
    public Boolean saveOne(@RequestBody ReqIdDto dto) {
        return indexService.saveOne(dto.getId());
    }

    @PostMapping("/search")
    @ApiOperation(value = "搜索", notes = "kevin")
    @ResponseBody
    public List<XmyContentMianSearchVo> search(@RequestBody QueryDto dto) {
        return indexService.search(dto.getText());
    }

    @PostMapping("/createIndex")
    @ApiOperation(value = "创建索引", notes = "kevin")
    public void createIndex() {
        indexService.createIndex();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "tangfan")
    public String update(@RequestBody ReqIdDto dto) {
        return indexService.update(dto.getId());
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "kevin")
    public void delete(@RequestBody ReqIdDto dto) {
        indexService.delete(dto.getId());
    }

    @PostMapping("/scroll")
    @ApiOperation(value = "分页", notes = "kevin")
    public ResScrollerDto scroll(@RequestBody ReqScrollerDto dto) {
        return indexService.scroller(dto);
    }


}
