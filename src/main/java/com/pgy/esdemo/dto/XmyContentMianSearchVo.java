package com.pgy.esdemo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页搜索模型
 */
@ApiModel("首页搜索模型")
@Data
public class XmyContentMianSearchVo {


    /**
     * 主键
     */
    private String contentId;

    /**
     * 内容名称/标题
     */
    @ApiModelProperty("内容名称/标题")
    private String contentName;

    /**
     * 简介
     */
    @ApiModelProperty("简介")
    private String contentIntroduction;

    /**
     * 封面图
     */
    @ApiModelProperty("封面图")
    private String coverImage;


    /**
     * 内容下级类型(1分类 2课件)
     */
    @ApiModelProperty("内容下级类型(1分类 2课件)")
    private String contentSonType;

    /**
     * 过渡页模板类型(0无 1一类 2二类 3三类)
     */
    @ApiModelProperty("过渡页模板类型(0无 1一类 2二类 3三类)")
    private String pageTemplateType;

    /**
     * 是否支持定制(0否 1是)
     */
    @ApiModelProperty("是否支持定制(0否 1是)")
    private String sheetStatus;

    /**
     * 定制表单模版Id
     */
    @ApiModelProperty("定制表单模版Id ")
    private String templateId;

    /**
     * 模块名称
     */
    @ApiModelProperty("模块名称")
    private String modleName;


    @ApiModelProperty("课件内容")
    private String content;
}
