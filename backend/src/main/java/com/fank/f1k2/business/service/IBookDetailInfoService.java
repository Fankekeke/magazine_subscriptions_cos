package com.fank.f1k2.business.service;

import com.fank.f1k2.business.entity.BookDetailInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IBookDetailInfoService extends IService<BookDetailInfo> {

    /**
     * 分页获取订阅源内容信息
     *
     * @param page           分页对象
     * @param bookDetailInfo 订阅源内容信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectDetailPage(Page<BookDetailInfo> page, BookDetailInfo bookDetailInfo);
}
