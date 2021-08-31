package com.pgy.esdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/5/14 9:05
 */
@Data
public class ReqScrollerDto extends PageDto {
    @ApiModelProperty("分页id")
    private String sid = "";

    @ApiModelProperty("查询内容")
    private String text;

    @ApiModelProperty("所属分类 1 全能校长 2 云端知识库   9全部  ")
    private String type;
}
