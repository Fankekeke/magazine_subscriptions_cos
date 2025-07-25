package com.fank.f1k2.business.controller;


import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.business.entity.AuthorInfo;
import com.fank.f1k2.business.service.IAuthorInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/author-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorInfoController {

    private final IAuthorInfoService authorInfoService;

    /**
     * 分页获取订阅源信息
     *
     * @param page       分页对象
     * @param authorInfo 订阅源信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<AuthorInfo> page, AuthorInfo authorInfo) {
        return R.ok(authorInfoService.selectAuthorPage(page, authorInfo));
    }

    /**
     * 热门订阅源统计
     *
     * @return 结果
     */
    @GetMapping("/selectListDetail")
    public R selectListDetail() {
        return R.ok(authorInfoService.selectListDetail());
    }

    /**
     * 订阅源信息详情
     *
     * @param id 订阅源ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(authorInfoService.selectAuthorDetail(id));
    }

    /**
     * 订阅源信息详情
     *
     * @param id 订阅源账户ID
     * @return 结果
     */
    @GetMapping("/detailByUserId/{id}")
    public R detailByUserId(@PathVariable("id") Integer id) {
        AuthorInfo authorInfo = authorInfoService.getOne(Wrappers.<AuthorInfo>lambdaQuery().eq(AuthorInfo::getUserId, id));
        return R.ok(authorInfoService.selectAuthorDetail(authorInfo.getId()));
    }

    /**
     * 订阅源信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(authorInfoService.list());
    }

    /**
     * 获取作者信息
     *
     * @return 结果
     */
    @GetMapping("/selectTopAuthor")
    public R selectTopAuthor() {
        return R.ok(authorInfoService.selectTopAuthor());
    }

    /**
     * 新增订阅源信息
     *
     * @param authorInfo 订阅源信息
     * @return 结果
     */
    @PostMapping
    public R save(AuthorInfo authorInfo) {
        authorInfo.setCode("AUTH-" + System.currentTimeMillis());
        authorInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(authorInfoService.save(authorInfo));
    }

    /**
     * 修改订阅源信息
     *
     * @param authorInfo 订阅源信息
     * @return 结果
     */
    @PutMapping
    public R edit(AuthorInfo authorInfo) {
        return R.ok(authorInfoService.updateById(authorInfo));
    }

    /**
     * 删除订阅源信息
     *
     * @param ids ids
     * @return 订阅源信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(authorInfoService.removeByIds(ids));
    }
}
