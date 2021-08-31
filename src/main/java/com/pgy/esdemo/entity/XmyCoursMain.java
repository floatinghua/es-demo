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
 * 课件主体表
 * </p>
 *
 * @author 
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="XmyCoursMain对象", description="课件主体表")
public class XmyCoursMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "courseware_id", type = IdType.INPUT)
    private String coursewareId;

    @ApiModelProperty(value = "课件名称")
    private String coursewareName;

    @ApiModelProperty(value = "文字介绍")
    private String introduction;

    @ApiModelProperty(value = "封面图")
    private String coverImage;

    @ApiModelProperty(value = "内容模板类型(1图文 2单图 3图视频 4视频)")
    private String templateType;

    @ApiModelProperty(value = "内容下级类型(1分类 2课件)")
    private String contentSonType;

    @ApiModelProperty(value = "审核状态(0待提交 1审核中 2已退回 3待上架 4上架中 5已下架)")
    private String auditStatus;

    @ApiModelProperty(value = "上传人")
    private Long uploadUserId;

    @ApiModelProperty(value = "上传人名称")
    private String uploudUserName;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "实物购买二维码")
    private String qrCode;

    @ApiModelProperty(value = "上传人部门")
    private Long deptId;

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
