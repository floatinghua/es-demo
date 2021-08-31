package com.pgy.esdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/8/25 9:01
 */
@Data
public class EsContent {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("内容")
    private String content;
}
