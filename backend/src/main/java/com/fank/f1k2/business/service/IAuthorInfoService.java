package com.fank.f1k2.business.service;

import com.fank.f1k2.business.entity.AuthorInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IAuthorInfoService extends IService<AuthorInfo> {

    /**
     * 分页获取订阅源信息
     *
     * @param page       分页对象
     * @param authorInfo 订阅源信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectAuthorPage(Page<AuthorInfo> page, AuthorInfo authorInfo);

    /**
     * 订阅源信息详情
     *
     * @param id 订阅源ID
     * @return 结果
     */
    AuthorInfo selectAuthorDetail(Integer id);

    /**
     * 获取作者信息
     *
     * @return 结果
     */
    List<AuthorInfo> selectTopAuthor();

    /**
     * 热门订阅源统计
     *
     * @return 结果
     */
    List<AuthorInfo> selectListDetail();
}
