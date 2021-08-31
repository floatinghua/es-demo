package com.pgy.esdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/5/17 8:45
 */
@Data
public class ReqIdDto {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("段落id")
    private String pid;
}
