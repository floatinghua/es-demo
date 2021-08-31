package com.pgy.esdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课件内容下的文章主体
 * </p>
 *
 * @author 
 * @since 2021-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xmy_cours_body_artic")
@ApiModel(value="XmyCoursBodyArtic对象", description="课件内容下的文章主体")
public class XmyCoursBodyArtic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "article_id", type = IdType.INPUT)
    private String articleId;

    @ApiModelProperty(value = "课件主键")
    private String coursewareId;

    @ApiModelProperty(value = "文章标题")
    private String articleName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "文章显示隐藏(0隐藏 1显示)")
    private String showType;

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
