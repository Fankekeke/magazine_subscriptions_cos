package com.fank.f1k2.business.service.impl;

import com.fank.f1k2.business.entity.EvaluateInfo;
import com.fank.f1k2.business.dao.EvaluateInfoMapper;
import com.fank.f1k2.business.service.IEvaluateInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class EvaluateInfoServiceImpl extends ServiceImpl<EvaluateInfoMapper, EvaluateInfo> implements IEvaluateInfoService {

    /**
     * 分页获取订阅源评价信息
     *
     * @param page         分页对象
     * @param evaluateInfo 订阅源评价信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectEvaluatePage(Page<EvaluateInfo> page, EvaluateInfo evaluateInfo) {
        return baseMapper.selectEvaluatePage(page, evaluateInfo);
    }

    /**
     * 根据订阅源ID获取评价信息
     *
     * @param bookId 订阅源ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectListByBookId(Integer bookId) {
        return baseMapper.selectListByBookId(bookId);
    }
}
