package com.fank.f1k2.business.service;

import com.fank.f1k2.business.entity.BookInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IBookInfoService extends IService<BookInfo> {

    /**
     * 分页获取订阅源信息
     *
     * @param page     分页对象
     * @param bookInfo 订阅源信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectBookPage(Page<BookInfo> page, BookInfo bookInfo);

    /**
     * 推荐列表
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<BookInfo> userCfRecommend(Integer userId);

    /**
     * 订阅源信息详情
     *
     * @param bookId 订阅源ID
     * @return 结果
     */
    LinkedHashMap<String, Object> selectDetailById(Integer bookId);

    /**
     * 根据用户ID获取订阅源信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<BookInfo> selectBookByUserId(Integer userId);

    /**
     * 文章统计列表
     *
     * @return 结果
     */
    List<BookInfo> selectListDetail();

    /**
     * 搜索
     *
     * @param key 关键字
     * @return 结果
     */
    List<BookInfo> selectListBySearch(String key);

    /**
     * 文章流量卡排行列表
     *
     * @return 结果
     */
    List<BookInfo> selectListTop();

    /**
     * 解析Rss列表 添加新记录
     * @param rssInfoList
     */
    void parsRssList(List<BookInfo> rssInfoList);
}
