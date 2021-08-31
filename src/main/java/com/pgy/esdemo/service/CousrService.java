package com.pgy.esdemo.service;

import com.pgy.esdemo.dto.ReqScrollerDto;
import com.pgy.esdemo.dto.ResScrollerDto;
import com.pgy.esdemo.dto.XmyCoursMainSearchDto;

import java.util.List;

/**
 * @Author: Kevin
 * @Description:
 * @Date: create in 2021/8/24 11:26
 */
public interface CousrService {
    void createCours();

    void saveOne(String id, String pid);

    List<XmyCoursMainSearchDto> search(String text);

    String update(String id, String pid);

    String updateAll(String id);

    void delete(String id, String pid);

    ResScrollerDto scroll(ReqScrollerDto dto);

    void saveAll();

}
