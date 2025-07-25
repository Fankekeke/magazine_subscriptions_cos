package com.fank.f1k2.business.service;

import com.fank.f1k2.business.entity.BookLikeInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IBookLikeInfoService extends IService<BookLikeInfo> {

    /**
     * 分页获取订阅源点赞信息
     *
     * @param page         分页对象
     * @param bookLikeInfo 订阅源点赞信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectBookLikePage(Page<BookLikeInfo> page, BookLikeInfo bookLikeInfo);
}
