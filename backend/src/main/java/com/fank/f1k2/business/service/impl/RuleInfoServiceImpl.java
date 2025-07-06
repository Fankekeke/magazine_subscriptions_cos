package com.fank.f1k2.business.service.impl;

import com.fank.f1k2.business.entity.RuleInfo;
import com.fank.f1k2.business.dao.RuleInfoMapper;
import com.fank.f1k2.business.service.IRuleInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class RuleInfoServiceImpl extends ServiceImpl<RuleInfoMapper, RuleInfo> implements IRuleInfoService {

    /**
     * 分页获取会员价格规格信息
     *
     * @param page     分页对象
     * @param ruleInfo 会员价格规格信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRulePage(Page<RuleInfo> page, RuleInfo ruleInfo) {
        return baseMapper.selectRulePage(page, ruleInfo);
    }
}
