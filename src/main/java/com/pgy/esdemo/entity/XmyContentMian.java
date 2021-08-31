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
 * 内容主体
 * </p>
 *
 * @author 
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xmy_content_mian")
@ApiModel(value="XmyContentMian对象", description="内容主体")
public class XmyContentMian implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "content_id", type = IdType.INPUT)
    private String contentId;

    @ApiModelProperty(value = "内容名称/标题")
    private String contentName;

    @ApiModelProperty(value = "简介")
    private String contentIntroduction;

    @ApiModelProperty(value = "封面图")
    private String coverImage;

    @ApiModelProperty(value = "上传人")
    private Long uploadUserId;

    @ApiModelProperty(value = "上传人名称")
    private String uploudUserName;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "上传人部门")
    private Long deptId;

    @ApiModelProperty(value = "标签(多个标签逗号分隔)")
    private String contentLabels;

    @ApiModelProperty(value = "内容下级类型(1分类 2课件)")
    private String contentSonType;

    @ApiModelProperty(value = "过渡页模板类型(0无 1一类 2二类 3三类)")
    private String pageTemplateType;

    @ApiModelProperty(value = "是否支持定制(0否 1是)")
    private String sheetStatus;

    @ApiModelProperty(value = "定制表单模版Id")
    private String templateId;

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

    @ApiModelProperty(value = "排序")
    private Integer sortBy;


}
