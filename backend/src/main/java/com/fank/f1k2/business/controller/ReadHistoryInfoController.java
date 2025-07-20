package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.business.entity.UserInfo;
import com.fank.f1k2.business.service.IUserInfoService;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.ReadHistoryInfo;
import com.fank.f1k2.business.service.IReadHistoryInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 阅读记录 控制层
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/cos/read-history-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadHistoryInfoController {

    private final IReadHistoryInfoService readHistoryInfoService;

    private final IUserInfoService userInfoService;

    /**
    * 分页获取阅读记录
    *
    * @param page       分页对象
    * @param queryFrom 阅读记录
    * @return 结果
    */
    @GetMapping("/page")
    public R page(Page<ReadHistoryInfo> page, ReadHistoryInfo queryFrom) {
        return R.ok(readHistoryInfoService.queryPage(page, queryFrom));
    }

    /**
    * 查询阅读记录详情
    *
    * @param id 主键ID
    * @return 结果
    */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(readHistoryInfoService.getById(id));
    }

    /**
    * 查询阅读记录列表
    *
    * @return 结果
    */
    @GetMapping("/list")
    public R list() {
        return R.ok(readHistoryInfoService.list());
    }

    /**
    * 新增阅读记录
    *
    * @param addFrom 阅读记录对象
    * @return 结果
    */
    @PostMapping
    public R save(ReadHistoryInfo addFrom) {
        // 获取用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, addFrom.getUserId()));
        if (userInfo != null) {
            addFrom.setUserId(userInfo.getId());
        }
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(readHistoryInfoService.save(addFrom));
    }

    /**
    * 修改阅读记录
    *
    * @param editFrom 阅读记录对象
    * @return 结果
    */
    @PutMapping
    public R edit(ReadHistoryInfo editFrom) {
        return R.ok(readHistoryInfoService.updateById(editFrom));
    }

    /**
    * 删除阅读记录
    *
    * @param ids 删除的主键ID
    * @return 结果
    */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(readHistoryInfoService.removeByIds(ids));
    }

}
