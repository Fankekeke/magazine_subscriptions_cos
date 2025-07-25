package com.fank.f1k2.business.dao;

import com.fank.f1k2.business.entity.EvaluateInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface EvaluateInfoMapper extends BaseMapper<EvaluateInfo> {

    /**
     * 分页获取订阅源评价信息
     *
     * @param page         分页对象
     * @param evaluateInfo 订阅源评价信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectEvaluatePage(Page<EvaluateInfo> page, @Param("evaluateInfo") EvaluateInfo evaluateInfo);

    /**
     * 根据订阅源ID获取评价信息
     *
     * @param bookId 订阅源ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectListByBookId(@Param("bookId") Integer bookId);
}
