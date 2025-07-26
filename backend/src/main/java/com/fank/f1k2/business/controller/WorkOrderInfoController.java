package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.business.entity.NotifyInfo;
import com.fank.f1k2.business.entity.UserInfo;
import com.fank.f1k2.business.service.INotifyInfoService;
import com.fank.f1k2.business.service.IUserInfoService;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WorkOrderInfo;
import com.fank.f1k2.business.service.IWorkOrderInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 工单信息 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/cos/work-order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkOrderInfoController {

    private final IWorkOrderInfoService workerInfoService;

    private final IUserInfoService userInfoService;

    private final INotifyInfoService notifyInfoService;

    /**
     * 分页获取工单信息
     *
     * @param page      分页对象
     * @param queryFrom 工单信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<WorkOrderInfo> page, WorkOrderInfo queryFrom) {
        return R.ok(workerInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询工单信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(workerInfoService.getById(id));
    }

    /**
     * 查询工单信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(workerInfoService.list());
    }

    /**
     * 新增工单信息
     *
     * @param addFrom 工单信息对象
     * @return 结果
     */
    @PostMapping
    public R save(WorkOrderInfo addFrom) {
        // 获取用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, addFrom.getUserId()));
        if (userInfo != null) {
            addFrom.setUserId(userInfo.getId());
        }
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        addFrom.setStatus("0");
        return R.ok(workerInfoService.save(addFrom));
    }

    /**
     * 查询工单回复
     *
     * @param quotationId 工单ID
     * @return 工单管理对象
     */
    @ApiOperation(value = "查询工单回复", notes = "通过工单ID获取对应的回复信息")
    @GetMapping("/queryReplyByQuotationId")
    public R queryReplyByQuotationId(Integer quotationId) {
        return R.ok(workerInfoService.queryReplyByQuotationId(quotationId));
    }

    /**
     * 回复用户
     *
     * @param content     回复内容
     * @param quotationId 工单ID
     * @return 工单管理对象
     */
    @ApiOperation(value = "回复用户", notes = "通过工单ID回复用户")
    @GetMapping("/replyUser")
    public R replyUser(String content, Integer quotationId) {
        return R.ok(workerInfoService.reply(content, quotationId, "2"));
    }

    /**
     * 回复管理员
     *
     * @param content     回复内容
     * @param quotationId 工单ID
     * @return 工单管理对象
     */
    @ApiOperation(value = "回复管理员", notes = "通过工单ID回复管理员")
    @GetMapping("/replyAdmin")
    public R replyAdmin(String content, Integer quotationId) {
        return R.ok(workerInfoService.reply(content, quotationId, "1"));
    }

    /**
     * 工单关闭
     *
     * @param quotationId 工单ID
     * @return 工单管理对象
     */
    @GetMapping("/workClose")
    public R workClose(Integer quotationId) {
        WorkOrderInfo workOrderInfo = workerInfoService.getById(quotationId);
        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setContent("工单【" + workOrderInfo.getContent() + "】已关闭");
        notifyInfo.setStatus("0");
        notifyInfo.setUserId(workOrderInfo.getUserId());
        notifyInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        notifyInfoService.save(notifyInfo);
        return R.ok(workerInfoService.update(Wrappers.<WorkOrderInfo>lambdaUpdate().set(WorkOrderInfo::getStatus, "1").set(WorkOrderInfo::getFinishDate, DateUtil.formatDateTime(new Date())).eq(WorkOrderInfo::getId, quotationId)));
    }

    /**
     * 修改工单信息
     *
     * @param editFrom 工单信息对象
     * @return 结果
     */
    @PutMapping
    public R edit(WorkOrderInfo editFrom) {
        return R.ok(workerInfoService.updateById(editFrom));
    }

    /**
     * 删除工单信息
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(workerInfoService.removeByIds(ids));
    }

}
