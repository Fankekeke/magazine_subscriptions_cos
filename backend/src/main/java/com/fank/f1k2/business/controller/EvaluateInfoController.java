package com.fank.f1k2.business.controller;


import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.business.entity.EvaluateInfo;
import com.fank.f1k2.business.entity.UserInfo;
import com.fank.f1k2.business.service.IEvaluateInfoService;
import com.fank.f1k2.business.service.IUserInfoService;
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
@RequestMapping("/cos/evaluate-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluateInfoController {

    private final IEvaluateInfoService evaluateInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取订阅源评价信息
     *
     * @param page         分页对象
     * @param evaluateInfo 订阅源评价信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<EvaluateInfo> page, EvaluateInfo evaluateInfo) {
        return R.ok(evaluateInfoService.selectEvaluatePage(page, evaluateInfo));
    }

    /**
     * 根据订阅源ID获取评价信息
     *
     * @param bookId 订阅源ID
     * @return 结果
     */
    @GetMapping("/selectListByBookId/list")
    public R selectListByBookId(Integer bookId) {
        return R.ok(evaluateInfoService.selectListByBookId(bookId));
    }

    /**
     * 订阅源评价信息详情
     *
     * @param id 订阅源评价ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(evaluateInfoService.getById(id));
    }

    /**
     * 订阅源评价信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(evaluateInfoService.list());
    }

    /**
     * 新增订阅源评价信息
     *
     * @param evaluateInfo 订阅源评价信息
     * @return 结果
     */
    @PostMapping
    public R save(EvaluateInfo evaluateInfo) {
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, evaluateInfo.getUserId()));
        if (userInfo != null) {
            evaluateInfo.setUserId(userInfo.getId());
        }
        evaluateInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(evaluateInfoService.save(evaluateInfo));
    }

    /**
     * 修改订阅源评价信息
     *
     * @param evaluateInfo 订阅源评价信息
     * @return 结果
     */
    @PutMapping
    public R edit(EvaluateInfo evaluateInfo) {
        return R.ok(evaluateInfoService.updateById(evaluateInfo));
    }

    /**
     * 删除订阅源评价信息
     *
     * @param ids ids
     * @return 订阅源评价信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(evaluateInfoService.removeByIds(ids));
    }
}
