package com.pgy.esdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/5/14 9:04
 */
@Data
public class PageDto {
    @ApiModelProperty("当前页数")
    private Integer page;

    @ApiModelProperty("每页数量")
    private Integer size;
}
