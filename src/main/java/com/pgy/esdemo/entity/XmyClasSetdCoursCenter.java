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
 * 内容下属分类与课件关系中间表
 * </p>
 *
 * @author 
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xmy_clas_setd_cours_center")
@ApiModel(value="XmyClasSetdCoursCenter对象", description="内容下属分类与课件关系中间表")
public class XmyClasSetdCoursCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容 下属分类主键")
    @TableId(value = "con_class_id", type = IdType.INPUT)
    private Long conClassId;

    @ApiModelProperty(value = "课件主体主键")
    private String coursewareId;


}
