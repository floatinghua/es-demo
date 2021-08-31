package com.pgy.esdemo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/8/25 8:52
 */
@Data
@ApiModel("课件")
public class XmyCoursMainSearchDto {
    @ApiModelProperty(value = "主键")
    private String coursewareId;

    @ApiModelProperty(value = "课件名称")
    private String coursewareName;

    @ApiModelProperty(value = "封面图")
    private String coverImage;

    @ApiModelProperty(value = "内容模板类型(1图文 2单图 3图视频 4视频)")
    private String templateType;

    @ApiModelProperty(value = "内容下级类型(1分类 2课件)")
    private String contentSonType;

    @ApiModelProperty(value = "审核状态(0待提交 1审核中 2已退回 3待上架 4上架中 5已下架)")
    private String auditStatus;

    @ApiModelProperty("内容")
    private List<EsContent> content;


}
