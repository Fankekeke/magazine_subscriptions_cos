package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.ReadHistoryInfo;
import com.fank.f1k2.business.dao.ReadHistoryInfoMapper;
import com.fank.f1k2.business.service.IReadHistoryInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class ReadHistoryInfoServiceImpl extends ServiceImpl<ReadHistoryInfoMapper, ReadHistoryInfo> implements IReadHistoryInfoService {

    /**
     * 分页获取阅读记录
     *
     * @param page      分页对象
     * @param queryFrom 阅读记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<ReadHistoryInfo> page, ReadHistoryInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
