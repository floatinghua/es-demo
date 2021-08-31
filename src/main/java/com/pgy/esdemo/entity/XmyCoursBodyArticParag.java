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
 * 课件内容下的文章下的段落
 * </p>
 *
 * @author 
 * @since 2021-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xmy_cours_body_artic_parag")
@ApiModel(value="XmyCoursBodyArticParag对象", description="课件内容下的文章下的段落")
public class XmyCoursBodyArticParag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "paragraph_id", type = IdType.INPUT)
    private String paragraphId;

    @ApiModelProperty(value = "文章主键")
    private String articleId;

    @ApiModelProperty(value = "段落标题")
    private String paragraphTitle;

    @ApiModelProperty(value = "段落内容")
    private String paragraphCotent;

    @ApiModelProperty(value = "去除格式的段落文本内容")
    private String content;

    @ApiModelProperty(value = "排序")
    private Integer sort;

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

    @ApiModelProperty(value = "百度富文本html格式的")
    private String paragraphHtml;

    @ApiModelProperty(value = "生成html的url地址")
    private String htmlUri;

    @ApiModelProperty(value = "markdown内容")
    private String mdContent;

    @ApiModelProperty(value = "markdownhtml")
    private String mdHtml;


}
