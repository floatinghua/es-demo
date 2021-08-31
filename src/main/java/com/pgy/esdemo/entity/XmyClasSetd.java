package com.pgy.esdemo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * 大分类设置表
 * </p>
 *
 * @author 
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xmy_clas_setd")
@ApiModel(value="XmyClasSetd对象", description="大分类设置表")
public class XmyClasSetd implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "classify_id", type = IdType.AUTO)
    private Long classifyId;

    @ApiModelProperty(value = "分类类型(1一级 ，2二级)")
    private String classifyType;

    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    @ApiModelProperty(value = "分类所属(1 DETT全能校长，2 云端知识库， 3 资源库)")
    private String classifyAuto;

    @ApiModelProperty(value = "所属父节点")
    private Long parentId;

    @ApiModelProperty(value = "祖籍列表")
    private String ancestors;

    @ApiModelProperty(value = "二级分类是否展示(0隐藏，1展示)")
    private String showFlag;

    @ApiModelProperty(value = "封面图")
    private String coverImage;

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

    @ApiModelProperty(value = "一级分类副标题")
    private String classifySubtitle;

    @ApiModelProperty(value = "一级分类slogan图")
    private String slogan;


}
