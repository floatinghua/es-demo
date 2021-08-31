package com.pgy.esdemo.service.impl;

import com.pgy.esdemo.dto.XmyContentMianSearchVo;
import com.pgy.esdemo.entity.XmyContentMian;
import com.pgy.esdemo.mapper.XmyContentMianMapper;
import com.pgy.esdemo.service.IXmyContentMianService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 内容主体 服务实现类
 * </p>
 *
 * @author
 * @since 2021-05-12
 */
@Service
public class XmyContentMianServiceImpl extends ServiceImpl<XmyContentMianMapper, XmyContentMian> implements IXmyContentMianService {

    @Override
    public List<XmyContentMianSearchVo> listContentModel(List<String> contentIds) {
        return this.baseMapper.listContentModel(contentIds);
    }
}
