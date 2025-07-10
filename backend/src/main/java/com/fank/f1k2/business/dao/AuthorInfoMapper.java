package com.fank.f1k2.business.dao;

import com.fank.f1k2.business.entity.AuthorInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface AuthorInfoMapper extends BaseMapper<AuthorInfo> {

    /**
     * 分页获取订阅源信息
     *
     * @param page       分页对象
     * @param authorInfo 订阅源信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectAuthorPage(Page<AuthorInfo> page, @Param("authorInfo") AuthorInfo authorInfo);
}
