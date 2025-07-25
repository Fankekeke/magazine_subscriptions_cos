package com.fank.f1k2.business.controller;


import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.business.entity.StyleInfo;
import com.fank.f1k2.business.service.IStyleInfoService;
import cn.hutool.core.date.DateUtil;
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
@RequestMapping("/cos/style-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StyleInfoController {

    private final IStyleInfoService styleInfoService;

    /**
     * 分页获取阅读样式信息
     *
     * @param page      分页对象
     * @param styleInfo 阅读样式信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<StyleInfo> page, StyleInfo styleInfo) {
        return R.ok(styleInfoService.selectStylePage(page, styleInfo));
    }

    /**
     * 阅读样式信息详情
     *
     * @param id 阅读样式ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(styleInfoService.getById(id));
    }

    /**
     * 阅读样式信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(styleInfoService.list());
    }

    /**
     * 新增阅读样式信息
     *
     * @param styleInfo 阅读样式信息
     * @return 结果
     */
    @PostMapping
    public R save(StyleInfo styleInfo) {
        styleInfo.setCode("STY-" + System.currentTimeMillis());
        styleInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(styleInfoService.save(styleInfo));
    }

    /**
     * 修改阅读样式信息
     *
     * @param styleInfo 阅读样式信息
     * @return 结果
     */
    @PutMapping
    public R edit(StyleInfo styleInfo) {
        return R.ok(styleInfoService.updateById(styleInfo));
    }

    /**
     * 删除阅读样式信息
     *
     * @param ids ids
     * @return 阅读样式信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(styleInfoService.removeByIds(ids));
    }
}
