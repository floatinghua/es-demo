package com.pgy.esdemo.service;

import com.pgy.esdemo.dto.XmyContentMianSearchVo;
import com.pgy.esdemo.entity.XmyContentMian;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 内容主体 服务类
 * </p>
 *
 * @author
 * @since 2021-05-12
 */
public interface IXmyContentMianService extends IService<XmyContentMian> {

    List<XmyContentMianSearchVo> listContentModel(List<String> contentIds);

}
