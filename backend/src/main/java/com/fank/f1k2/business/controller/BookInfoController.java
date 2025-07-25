package com.fank.f1k2.business.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.business.entity.AuthorInfo;
import com.fank.f1k2.business.entity.BookInfo;
import com.fank.f1k2.business.entity.UserInfo;
import com.fank.f1k2.business.service.IAuthorInfoService;
import com.fank.f1k2.business.service.IBookInfoService;
import com.fank.f1k2.business.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/book-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookInfoController {

    private final IBookInfoService bookInfoService;

    private final IAuthorInfoService authorInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取订阅源信息
     *
     * @param page     分页对象
     * @param bookInfo 订阅源信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<BookInfo> page, BookInfo bookInfo) {
        return R.ok(bookInfoService.selectBookPage(page, bookInfo));
    }

    /**
     * 订阅源状态审核
     *
     * @param id     主键ID
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/audit")
    public R audit(Integer id, String status) {
        return R.ok(bookInfoService.update(Wrappers.<BookInfo>lambdaUpdate().set(BookInfo::getStatus, status).eq(BookInfo::getId, id)));
    }

    /**
     * 订阅源信息详情
     *
     * @param id 订阅源ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bookInfoService.selectDetailById(id));
    }

    /**
     * 根据用户ID获取订阅源信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/list/byUserId")
    public R selectBookByUserId(Integer userId) {
        return R.ok(bookInfoService.selectBookByUserId(userId));
    }

    /**
     * 文章统计列表
     *
     * @return 结果
     */
    @GetMapping("/selectListDetail")
    public R selectListDetail() {
        return R.ok(bookInfoService.selectListDetail());
    }

    /**
     * 搜索
     *
     * @param key 关键字
     * @return 结果
     */
    @GetMapping("/selectListBySearch/{key}")
    public R selectListBySearch(@PathVariable("key") String key) {
        return R.ok(bookInfoService.selectListBySearch(key));
    }

    /**
     * 用户CF推荐
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/userCfRecommend")
    public R userCfRecommend(Integer userId) {
        UserInfo userInfo =  userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        return R.ok(bookInfoService.userCfRecommend(userInfo.getId()));
    }

    /**
     * 文章流量卡排行列表
     *
     * @return 结果
     */
    @GetMapping("/selectListTop")
    public R selectListTop() {
        return R.ok(bookInfoService.selectListTop());
    }

    /**
     * rss解析
     *
     * @return 订阅源信息
     */
    @Async
    @GetMapping("/rssParse")
    public R rssParse() {
        List<BookInfo> bookInfoList = bookInfoService.list();
        if (CollectionUtil.isNotEmpty(bookInfoList)) {
            bookInfoService.parsRssList(bookInfoList);
        }
        return R.ok(true);
    }

    /**
     * 订阅源信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(bookInfoService.list());
    }

    /**
     * 新增订阅源信息
     *
     * @param bookInfo 订阅源信息
     * @return 结果
     */
    @PostMapping
    public R save(BookInfo bookInfo) {
        // 获取作者信息
        AuthorInfo authorInfo = authorInfoService.getOne(Wrappers.<AuthorInfo>lambdaQuery().eq(AuthorInfo::getUserId, bookInfo.getAuthorId()));
        if (authorInfo != null) {
            bookInfo.setAuthorId(authorInfo.getId());
            bookInfo.setRssAuthor(authorInfo.getName());
        }

        bookInfo.setCode("BK-" + System.currentTimeMillis());
        bookInfo.setStatus("0");
        bookInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        bookInfo.setUpdateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bookInfoService.save(bookInfo));
    }

    /**
     * 修改订阅源信息
     *
     * @param bookInfo 订阅源信息
     * @return 结果
     */
    @PutMapping
    public R edit(BookInfo bookInfo) {
        bookInfo.setStatus("0");
        return R.ok(bookInfoService.updateById(bookInfo));
    }

    /**
     * 删除订阅源信息
     *
     * @param ids ids
     * @return 订阅源信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bookInfoService.removeByIds(ids));
    }
}
