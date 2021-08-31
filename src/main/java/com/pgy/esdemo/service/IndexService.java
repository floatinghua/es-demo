package com.pgy.esdemo.service;

import com.pgy.esdemo.dto.ReqScrollerDto;
import com.pgy.esdemo.dto.ResScrollerDto;
import com.pgy.esdemo.dto.XmyContentMianSearchVo;

import java.util.List;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/5/12 10:40
 */
public interface IndexService {
    void saveToes();

    List<XmyContentMianSearchVo> search(String text);

    void createIndex();

    String update(String id);


    void delete(String id);

    ResScrollerDto scroller(ReqScrollerDto dto);

    Boolean saveOne(String id);



}
