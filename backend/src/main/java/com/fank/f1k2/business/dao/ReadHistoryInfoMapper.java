package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.ReadHistoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface ReadHistoryInfoMapper extends BaseMapper<ReadHistoryInfo> {

    /**
     * 分页获取阅读记录
     *
     * @param page      分页对象
     * @param queryFrom 阅读记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<ReadHistoryInfo> page, @Param("queryFrom") ReadHistoryInfo queryFrom);
}
