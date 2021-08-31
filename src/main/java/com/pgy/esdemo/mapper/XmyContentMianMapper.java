package com.pgy.esdemo.mapper;

import com.pgy.esdemo.dto.XmyContentMianSearchVo;
import com.pgy.esdemo.entity.XmyContentMian;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 内容主体 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-05-12
 */
public interface XmyContentMianMapper extends BaseMapper<XmyContentMian> {

    List<XmyContentMianSearchVo> listContentModel(@Param("contentIds") List<String> contentIds);

    List<String> searchFirst(String text);

    List<String> searchSecond(String text);

    List<String> searchThird(String text);

    List<String> getPara(@Param("contentId") String contentId, @Param("contentSonType") String contentSonType, @Param("conIds") List<String> conIds);

    List<String> selectConIds(String contentId);
}
