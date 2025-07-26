package com.fank.f1k2.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fank.f1k2.common.utils.ItemCF;
import com.fank.f1k2.business.dao.AuthorInfoMapper;
import com.fank.f1k2.business.dao.BookDetailInfoMapper;
import com.fank.f1k2.business.dao.UserInfoMapper;
import com.fank.f1k2.business.entity.*;
import com.fank.f1k2.business.dao.BookInfoMapper;
import com.fank.f1k2.business.service.IBookInfoService;
import com.fank.f1k2.business.service.IBookLikeInfoService;
import com.fank.f1k2.business.service.IEvaluateInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.common.utils.RssParse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements IBookInfoService {

    private final AuthorInfoMapper authorInfoMapper;

    private final BookDetailInfoMapper bookDetailInfoMapper;

    private final IEvaluateInfoService evaluateInfoService;

    private final IBookLikeInfoService bookLikeInfoService;

    private final UserInfoMapper userInfoMapper;

    private final SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

    /**
     * 分页获取订阅源信息
     *
     * @param page     分页对象
     * @param bookInfo 订阅源信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectBookPage(Page<BookInfo> page, BookInfo bookInfo) {
        return baseMapper.selectBookPage(page, bookInfo);
    }

    /**
     * 推荐列表
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<BookInfo> userCfRecommend(Integer userId) {
        // 获取所有评论信息
        List<EvaluateInfo> evaluateInfoList = evaluateInfoService.list();
        // 根据用户ID与订阅源ID转map
        Map<String, List<EvaluateInfo>> evaluateInfoMap = evaluateInfoList.stream().collect(Collectors.groupingBy(e -> e.getUserId() + "|" + e.getBookId()));
        // 获取所有点赞信息
        List<BookLikeInfo> bookLikeInfoList = bookLikeInfoService.list();
        // 根据用户ID与订阅源ID转map
        Map<String, List<BookLikeInfo>> bookLikeInfoMap = bookLikeInfoList.stream().collect(Collectors.groupingBy(e -> e.getUserId() + "|" + e.getBookId()));

        // 所有订阅源信息
        List<BookInfo> bookInfoList = this.list();
        // 所有用户信息
        List<UserInfo> userInfoList = userInfoMapper.selectList(Wrappers.<UserInfo>lambdaQuery());

        if (CollectionUtil.isEmpty(bookInfoList) || CollectionUtil.isEmpty(userInfoList)) {
            return Collections.emptyList();
        }

        List<RelateDTO> data= new ArrayList<>();
        // 评论一次+5，点赞一次+10
        for (BookInfo bookInfo : bookInfoList) {
            for (UserInfo userInfo : userInfoList) {
                // 获取此用户对应的评论次数
                List<EvaluateInfo> evaluateInfos = evaluateInfoMap.get(userInfo.getId() + "|" + bookInfo.getId());
                // 获取此用户对应的点赞次数
                List<BookLikeInfo> bookLikeInfos = bookLikeInfoMap.get(userInfo.getId() + "|" + bookInfo.getId());
                if (CollectionUtil.isNotEmpty(evaluateInfos) || CollectionUtil.isNotEmpty(bookLikeInfos)) {
                    // 获取此用户对应的评论次数
                    int evaluateCount = CollectionUtil.isEmpty(evaluateInfos) ? 0 : evaluateInfos.size();
                    // 获取此用户对应的点赞次数
                    int likeCount = CollectionUtil.isEmpty(bookLikeInfos) ? 0 : bookLikeInfos.size();
                    // 评论一次+5，点赞一次+10
                    data.add(new RelateDTO(userInfo.getId(), bookInfo.getId(), NumberUtil.add(NumberUtil.mul(evaluateCount, 5), NumberUtil.mul(likeCount, 10))));
                } else {
                    data.add(new RelateDTO(userInfo.getId(), bookInfo.getId(), 0.0));
                }
            }
        }
        // 获取到推荐的id
        List<Integer> recommendations = ItemCF.recommend(userId, data);
        if (CollectionUtil.isEmpty(recommendations)) {
            return Collections.emptyList();
        }

        // 查询所有订阅源信息
        List<BookInfo> bookInfoListResult = this.list(Wrappers.<BookInfo>lambdaQuery().in(BookInfo::getId, recommendations));
        if (CollectionUtil.isEmpty(bookInfoListResult)) {
            return bookInfoListResult;
        }

        // 获取所有作者
        List<AuthorInfo> authorInfoList = authorInfoMapper.selectList(Wrappers.<AuthorInfo>lambdaQuery());
        Map<Integer, AuthorInfo> authorInfoMap = authorInfoList.stream().collect(Collectors.toMap(AuthorInfo::getId, e -> e));

        for (BookInfo bookInfo : bookInfoListResult) {
            if (CollectionUtil.isNotEmpty(authorInfoMap) && authorInfoMap.get(bookInfo.getAuthorId()) != null) {
                bookInfo.setAuthorInfo(authorInfoMap.get(bookInfo.getAuthorId()));
            }
        }
        // 按照recommendations排序
        bookInfoListResult.sort(Comparator.comparingInt(bookInfo -> recommendations.indexOf(bookInfo.getId())));
        return bookInfoListResult;
    }

    /**
     * 订阅源信息详情
     *
     * @param bookId 订阅源ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectDetailById(Integer bookId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("book", null);
                put("detail", Collections.emptyList());
                put("author", null);
            }
        };

        // 订阅源信息
        BookInfo bookInfo = this.getById(bookId);
        result.put("book", bookInfo);
        // 订阅源内容
        List<BookDetailInfo> detailList = bookDetailInfoMapper.selectList(Wrappers.<BookDetailInfo>lambdaQuery().eq(BookDetailInfo::getBookId, bookId).orderByDesc(BookDetailInfo::getIndexNo));
        result.put("detail", detailList);
        // 作者信息
        AuthorInfo authorInfo = authorInfoMapper.selectById(bookInfo.getAuthorId());
        result.put("author", authorInfo);

        return result;
    }

    /**
     * 根据用户ID获取订阅源信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<BookInfo> selectBookByUserId(Integer userId) {
        // 获取作者信息
        AuthorInfo authorInfo = authorInfoMapper.selectOne(Wrappers.<AuthorInfo>lambdaQuery().eq(AuthorInfo::getUserId, userId));

        if (authorInfo == null) {
            return Collections.emptyList();
        }

        return this.list(Wrappers.<BookInfo>lambdaQuery().eq(BookInfo::getAuthorId, authorInfo.getId()));
    }

    /**
     * 文章统计列表
     *
     * @return 结果
     */
    @Override
    public List<BookInfo> selectListDetail() {
        // 查询所有订阅源信息
        List<BookInfo> bookInfoList = this.list();
        if (CollectionUtil.isEmpty(bookInfoList)) {
            return bookInfoList;
        }

        // 获取所有作者
        List<AuthorInfo> authorInfoList = authorInfoMapper.selectList(Wrappers.<AuthorInfo>lambdaQuery());
        Map<Integer, AuthorInfo> authorInfoMap = authorInfoList.stream().collect(Collectors.toMap(AuthorInfo::getId, e -> e));

        for (BookInfo bookInfo : bookInfoList) {
            if (CollectionUtil.isNotEmpty(authorInfoMap) && authorInfoMap.get(bookInfo.getAuthorId()) != null) {
                bookInfo.setAuthorInfo(authorInfoMap.get(bookInfo.getAuthorId()));
            }
        }
        return bookInfoList;
    }

    /**
     * 搜索
     *
     * @param key 关键字
     * @return 结果
     */
    @Override
    public List<BookInfo> selectListBySearch(String key) {
        // 查询所有订阅源信息
        List<BookInfo> bookInfoList = this.list();
        if (CollectionUtil.isEmpty(bookInfoList)) {
            return bookInfoList;
        }

        // 获取所有作者
        List<AuthorInfo> authorInfoList = authorInfoMapper.selectList(Wrappers.<AuthorInfo>lambdaQuery());
        Map<Integer, AuthorInfo> authorInfoMap = authorInfoList.stream().collect(Collectors.toMap(AuthorInfo::getId, e -> e));

        for (BookInfo bookInfo : bookInfoList) {
            if (CollectionUtil.isNotEmpty(authorInfoMap) && authorInfoMap.get(bookInfo.getAuthorId()) != null) {
                bookInfo.setAuthorInfo(authorInfoMap.get(bookInfo.getAuthorId()));
            }
        }
        if (CollectionUtil.isEmpty(bookInfoList)) {
            return Collections.emptyList();
        }

        // 根据书名和作者过滤
        bookInfoList = bookInfoList.stream().filter(e -> e.getName().contains(key) || e.getAuthorInfo().getName().contains(key)).collect(Collectors.toList());
        return bookInfoList;
    }

    /**
     * 文章流量卡排行列表
     *
     * @return 结果
     */
    @Override
    public List<BookInfo> selectListTop() {
        // 查询所有订阅源信息
        List<BookInfo> bookInfoList = this.selectListDetail();
        if (CollectionUtil.isEmpty(bookInfoList)) {
            return bookInfoList;
        }

        List<BookDetailInfo> bookDetailList = bookDetailInfoMapper.selectList(Wrappers.<BookDetailInfo>lambdaQuery());
        Map<String, List<BookDetailInfo>> bookDetailMap = bookDetailList.stream().collect(Collectors.groupingBy(BookDetailInfo::getBookId));

        for (BookInfo bookInfo : bookInfoList) {
            if (CollectionUtil.isNotEmpty(bookDetailMap) && CollectionUtil.isNotEmpty(bookDetailMap.get(bookInfo.getCode()))) {
                List<BookDetailInfo> detailInfoList = bookDetailMap.get(bookInfo.getCode());
                Integer count = detailInfoList.stream().mapToInt(BookDetailInfo::getViews).sum();
                bookInfo.setViews(count);
            } else {
                bookInfo.setViews(0);
            }
        }

        // 排序
        return bookInfoList.stream().sorted(Comparator.comparing(BookInfo::getViews).reversed()).collect(Collectors.toList());
    }

    @Override
    public void parsRssList(List<BookInfo> rssInfoList) {
        for (BookInfo rssInfo : rssInfoList) {
            System.out.println("=======>" + rssInfo.getName());
            if (StrUtil.isNotEmpty(rssInfo.getRssUrl())) {
                List<BookDetailInfo> rssHistoryList = RssParse.parseRss(rssInfo.getRssUrl());
                // 倒序
                Collections.reverse(rssHistoryList);
                rssHistoryList.forEach(rssHistory -> {
                    try {
                        rssHistory.setViews(0);
                        rssHistory.setCheckFlag("0");
                        rssHistory.setWords(StrUtil.length(rssHistory.getContent()));
                        rssHistory.setBookId(rssInfo.getCode());
                        // 判断日期是否为空 为空进行标题判断
                        if (rssHistory.getPublishedDate() == null) {
                            if (bookDetailInfoMapper.selectList(Wrappers.<BookDetailInfo>lambdaQuery().eq(rssHistory.getTitle() != null, BookDetailInfo::getTitle, rssHistory.getTitle())).size() == 0) {
                                bookDetailInfoMapper.insert(rssHistory);
                                System.out.println("入库");
                            }
                        } else {
                            // 获取当前rss库中最大时间记录
                            BookDetailInfo rssHistoryMax = bookDetailInfoMapper.getMaxHistoryByDate(rssHistory);
                            if (rssHistoryMax != null) {
                                // 判断发布日期是否大于库里最大日期
                                if (format0.parse(format0.format(sdf.parse(rssHistory.getPublishedDate()))).getTime() > format0.parse(rssHistoryMax.getPublishedDate()).getTime()) {
                                    rssHistory.setPublishedDate(format0.format(sdf.parse(rssHistory.getPublishedDate())));
                                    bookDetailInfoMapper.insert(rssHistory);
                                    System.out.println("入库");
                                }
                            } else {
                                rssHistory.setPublishedDate(format0.format(sdf.parse(rssHistory.getPublishedDate())));
                                bookDetailInfoMapper.insert(rssHistory);
                                System.out.println("入库");
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
