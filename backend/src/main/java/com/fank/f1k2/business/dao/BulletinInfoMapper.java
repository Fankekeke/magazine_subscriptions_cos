package com.fank.f1k2.business.dao;

import com.fank.f1k2.business.entity.BulletinInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface BulletinInfoMapper extends BaseMapper<BulletinInfo> {

    /**
     * 分页获取公告信息
     *
     * @param page         分页对象
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectBulletinByPage(Page<BulletinInfo> page, @Param("bulletinInfo") BulletinInfo bulletinInfo);
}
