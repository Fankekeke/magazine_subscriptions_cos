package com.fank.f1k2.business.service.impl;

import com.fank.f1k2.business.entity.BookLikeInfo;
import com.fank.f1k2.business.dao.BookLikeInfoMapper;
import com.fank.f1k2.business.service.IBookLikeInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class BookLikeInfoServiceImpl extends ServiceImpl<BookLikeInfoMapper, BookLikeInfo> implements IBookLikeInfoService {

    /**
     * 分页获取订阅源点赞信息
     *
     * @param page         分页对象
     * @param bookLikeInfo 订阅源点赞信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectBookLikePage(Page<BookLikeInfo> page, BookLikeInfo bookLikeInfo) {
        return baseMapper.selectBookLikePage(page, bookLikeInfo);
    }
}
