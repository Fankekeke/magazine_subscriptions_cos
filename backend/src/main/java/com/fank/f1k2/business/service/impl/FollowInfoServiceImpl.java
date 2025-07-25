package com.fank.f1k2.business.service.impl;

import com.fank.f1k2.business.dao.AuthorInfoMapper;
import com.fank.f1k2.business.dao.BookDetailInfoMapper;
import com.fank.f1k2.business.dao.BookInfoMapper;
import com.fank.f1k2.business.entity.AuthorInfo;
import com.fank.f1k2.business.entity.BookInfo;
import com.fank.f1k2.business.entity.FollowInfo;
import com.fank.f1k2.business.dao.FollowInfoMapper;
import com.fank.f1k2.business.service.IFollowInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FollowInfoServiceImpl extends ServiceImpl<FollowInfoMapper, FollowInfo> implements IFollowInfoService {

    private final AuthorInfoMapper authorInfoMapper;

    private final BookInfoMapper bookInfoMapper;

    private final BookDetailInfoMapper bookDetailInfoMapper;

    /**
     * 分页获取用户关注信息
     *
     * @param page       分页对象
     * @param followInfo 用户关注信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectFollowPage(Page<FollowInfo> page, FollowInfo followInfo) {
        return baseMapper.selectFollowPage(page, followInfo);
    }

    /**
     * 用户关注信息详情
     *
     * @param id 用户关注ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectDetail(Integer id) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("author", null);
                put("books", Collections.emptyList());
                put("follow", null);
            }
        };
        // 关注信息
        FollowInfo followInfo = this.getById(id);
        result.put("follow", followInfo);
        // 作者信息
        AuthorInfo authorInfo = authorInfoMapper.selectById(followInfo.getAuthorId());
        result.put("author", authorInfo);

        // 订阅源信息
        List<BookInfo> bookInfoList = bookInfoMapper.selectList(Wrappers.<BookInfo>lambdaQuery().eq(BookInfo::getAuthorId, authorInfo.getId()));
        result.put("books", bookInfoList);
        return result;
    }
}
