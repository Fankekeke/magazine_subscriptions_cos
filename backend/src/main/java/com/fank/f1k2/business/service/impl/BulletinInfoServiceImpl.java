package com.fank.f1k2.business.service.impl;

import com.fank.f1k2.business.dao.BulletinInfoMapper;
import com.fank.f1k2.business.entity.BulletinInfo;
import com.fank.f1k2.business.service.IBulletinInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class BulletinInfoServiceImpl extends ServiceImpl<BulletinInfoMapper, BulletinInfo> implements IBulletinInfoService {

    /**
     * 分页获取公告信息
     *
     * @param page         分页对象
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectBulletinByPage(Page<BulletinInfo> page, BulletinInfo bulletinInfo) {
        return baseMapper.selectBulletinByPage(page, bulletinInfo);
    }
}
