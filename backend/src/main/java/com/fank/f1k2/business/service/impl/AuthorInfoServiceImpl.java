package com.fank.f1k2.business.service.impl;

import com.fank.f1k2.business.dao.BookInfoMapper;
import com.fank.f1k2.business.dao.FollowInfoMapper;
import com.fank.f1k2.business.entity.AuthorInfo;
import com.fank.f1k2.business.dao.AuthorInfoMapper;
import com.fank.f1k2.business.entity.BookInfo;
import com.fank.f1k2.business.entity.FollowInfo;
import com.fank.f1k2.business.service.IAuthorInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorInfoServiceImpl extends ServiceImpl<AuthorInfoMapper, AuthorInfo> implements IAuthorInfoService {

    private final FollowInfoMapper followInfoMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * 分页获取订阅源信息
     *
     * @param page       分页对象
     * @param authorInfo 订阅源信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectAuthorPage(Page<AuthorInfo> page, AuthorInfo authorInfo) {
        return baseMapper.selectAuthorPage(page, authorInfo);
    }

    /**
     * 订阅源信息详情
     *
     * @param id 订阅源ID
     * @return 结果
     */
    @Override
    public AuthorInfo selectAuthorDetail(Integer id) {
        AuthorInfo authorInfo = this.getById(id);
        // 粉丝数量
        Integer count = followInfoMapper.selectCount(Wrappers.<FollowInfo>lambdaQuery().eq(FollowInfo::getAuthorId, id));
        authorInfo.setFansNum(count);

        // 订阅源信息
        List<BookInfo> bookInfoList = bookInfoMapper.selectList(Wrappers.<BookInfo>lambdaQuery().eq(BookInfo::getAuthorId, id));
        authorInfo.setBookInfoList(bookInfoList);
        return authorInfo;
    }

    /**
     * 获取作者信息
     *
     * @return 结果
     */
    @Override
    public List<AuthorInfo> selectTopAuthor() {
        // 获取所有作者信息
        List<AuthorInfo> authorInfoList = this.list();
        if (CollectionUtil.isEmpty(authorInfoList)) {
            return authorInfoList;
        }

        List<FollowInfo> followInfos = followInfoMapper.selectList(Wrappers.<FollowInfo>lambdaQuery());
        Map<Integer, List<FollowInfo>> followInfoMap = followInfos.stream().collect(Collectors.groupingBy(FollowInfo::getAuthorId));

        List<BookInfo> bookInfoList = bookInfoMapper.selectList(Wrappers.<BookInfo>lambdaQuery());
        Map<Integer, List<BookInfo>> bookInfoMap = bookInfoList.stream().collect(Collectors.groupingBy(BookInfo::getAuthorId));

        for (AuthorInfo authorInfo : authorInfoList) {
            if (CollectionUtil.isNotEmpty(followInfoMap) && CollectionUtil.isNotEmpty(followInfoMap.get(authorInfo.getId()))) {
                authorInfo.setFansNum(followInfoMap.get(authorInfo.getId()).size());
            } else {
                authorInfo.setFansNum(0);
            }

            if (CollectionUtil.isNotEmpty(bookInfoMap) && CollectionUtil.isNotEmpty(bookInfoMap.get(authorInfo.getId()))) {
                authorInfo.setBookInfoList(bookInfoMap.get(authorInfo.getId()));
            } else {
                authorInfo.setBookInfoList(Collections.emptyList());
            }
        }
        return authorInfoList;
    }

    /**
     * 热门订阅源统计
     *
     * @return 结果
     */
    @Override
    public List<AuthorInfo> selectListDetail() {
        List<AuthorInfo> authorInfoList = this.selectTopAuthor();
        return authorInfoList.stream().sorted(Comparator.comparing(AuthorInfo::getFansNum).reversed()).collect(Collectors.toList());
    }
}
