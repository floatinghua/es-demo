package com.pgy.esdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 内容下属分类设置
 * </p>
 *
 * @author 
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xmy_content_clas_setd")
@ApiModel(value="XmyContentClasSetd对象", description="内容下属分类设置")
public class XmyContentClasSetd implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "con_class_id", type = IdType.AUTO)
    private Long conClassId;

    @ApiModelProperty(value = "分类类型(1一级 2二级)")
    private String conClassType;

    @ApiModelProperty(value = "分类名称")
    private String conClassName;

    @ApiModelProperty(value = "简介")
    private String introduction;

    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @ApiModelProperty(value = "祖籍列表")
    private String ancestors;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "一级分类slogan图")
    private String slogan;

    @ApiModelProperty(value = "封面图")
    private String coverImage;

    @ApiModelProperty(value = "逻辑删除(0删除 1存在)")
    private String useFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "备注")
    private String remark;


}
