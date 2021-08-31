package com.pgy.esdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/5/14 9:05
 */
@Data
public class ResScrollerDto {

    @ApiModelProperty("分页过期刷新页面 true 刷新 false 不刷新")
    private boolean flush = false;

    @ApiModelProperty("分页id")
    private String sid;

//    @ApiModelProperty("返回类")
//    private List<XmyContentMianSearchVo> res;

    @ApiModelProperty("全能校长搜索结果")
    private List<XmyContentMianSearchVo> DettContentList;


    @ApiModelProperty("云端知识库搜索结果")
    private List<XmyContentMianSearchVo> CloudContentList;


    @ApiModelProperty("全部搜索结果")
    private List<XmyCoursMainSearchDto> list;
}
