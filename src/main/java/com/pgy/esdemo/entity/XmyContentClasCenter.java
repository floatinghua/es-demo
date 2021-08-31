package com.pgy.esdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 内容主体与大分类关系中间表
 * </p>
 *
 * @author 
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xmy_content_clas_center")
@ApiModel(value="XmyContentClasCenter对象", description="内容主体与大分类关系中间表")
public class XmyContentClasCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容主体主键")
    @TableId(value = "content_id", type = IdType.INPUT)
    private String contentId;

    @ApiModelProperty(value = "类型主键")
    private Long classifyId;


}
