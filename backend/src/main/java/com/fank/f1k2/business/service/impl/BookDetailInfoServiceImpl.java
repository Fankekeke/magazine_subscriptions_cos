package com.fank.f1k2.business.service.impl;

import com.fank.f1k2.business.entity.BookDetailInfo;
import com.fank.f1k2.business.dao.BookDetailInfoMapper;
import com.fank.f1k2.business.service.IBookDetailInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class BookDetailInfoServiceImpl extends ServiceImpl<BookDetailInfoMapper, BookDetailInfo> implements IBookDetailInfoService {

    /**
     * 分页获取订阅源内容信息
     *
     * @param page           分页对象
     * @param bookDetailInfo 订阅源内容信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectDetailPage(Page<BookDetailInfo> page, BookDetailInfo bookDetailInfo) {
        return baseMapper.selectDetailPage(page, bookDetailInfo);
    }
}
