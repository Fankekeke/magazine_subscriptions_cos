package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
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
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/work-order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkOrderInfoController {

    private final IWorkOrderInfoService workerInfoService;

    /**
    * 分页获取工单信息
    *
    * @param page       分页对象
    * @param queryFrom 工单信息
    * @return 结果
    */
    @GetMapping("/page")
    public R page(Page<WorkOrderInfo> page, WorkOrderInfo queryFrom) {
        return R.ok();
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
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(workerInfoService.save(addFrom));
    }

    /**
     * 查询工单回复
     *
     * @param quotationId 工单ID
     * @return 采购计划工单管理对象
     */
    @ApiOperation(value = "查询工单回复", notes = "通过工单ID获取对应的回复信息")
    @GetMapping("/queryReplyByQuotationId")
    public R queryReplyByQuotationId(Integer quotationId) {
        return R.ok(workerInfoService.queryReplyByQuotationId(quotationId));
    }

    /**
     * 回复科研人员
     *
     * @param content 回复内容
     * @param quotationId 工单ID
     * @return 采购计划工单管理对象
     */
    @ApiOperation(value = "回复科研人员", notes = "通过工单ID回复科研人员")
    @GetMapping("/replySupplier")
    public R replySupplier(String content, Integer quotationId) {
        return R.ok(workerInfoService.reply(content, quotationId, "2"));
    }

    /**
     * 回复管理员
     *
     * @param content 回复内容
     * @param quotationId 工单ID
     * @return 采购计划工单管理对象
     */
    @ApiOperation(value = "回复管理员", notes = "通过工单ID回复管理员")
    @GetMapping("/replyAdmin")
    public R replyAdmin(String content, Integer quotationId) {
        return R.ok(workerInfoService.reply(content, quotationId, "1"));
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
