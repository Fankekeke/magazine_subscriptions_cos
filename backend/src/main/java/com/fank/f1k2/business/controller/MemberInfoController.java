package com.fank.f1k2.business.controller;


import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.business.entity.*;
import com.fank.f1k2.business.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/member-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberInfoController {

    private final IUserInfoService userInfoService;

    private final IMemberOrderInfoService memberOrderInfoService;

    private final IRuleInfoService ruleInfoService;

    private final IMemberInfoService memberInfoService;

    private final IBulletinInfoService bulletinInfoService;

    private final INotifyInfoService notifyInfoService;

    /**
     * 根据用户ID获取会员信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/member/{userId}")
    public R selectMemberByUserId(@PathVariable("userId") Integer userId) {
        // 返回信息
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        result.put("user", userInfo);

        // 公告信息
        List<BulletinInfo> bulletinInfoList = bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getType, 1));
        result.put("bulletin", bulletinInfoList);
        // 会员信息
        List<MemberInfo> memberInfos = memberInfoService.list(Wrappers.<MemberInfo>lambdaQuery().eq(MemberInfo::getUserId, userInfo.getId()));
        if (CollectionUtil.isNotEmpty(memberInfos)) {
            for (MemberInfo memberInfo : memberInfos) {
                if (DateUtil.isIn(new Date(), DateUtil.parseDateTime(memberInfo.getStartDate()), DateUtil.parseDateTime(memberInfo.getEndDate()))) {
                    RuleInfo ruleInfo = ruleInfoService.getById(memberInfo.getMemberLevel());
                    memberInfo.setRuleName(ruleInfo.getName());
                    result.put("member", memberInfo);
                    return R.ok(result);
                }
            }
        } else {
            result.put("member", null);
        }
        return R.ok(result);
    }

    /**
     * 查询用户是否会员
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/over/{userId}")
    public R selectUserMemberOver(@PathVariable("userId") Integer userId) {
        // 用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        // 会员信息
        List<MemberInfo> memberInfos = memberInfoService.list(Wrappers.<MemberInfo>lambdaQuery().eq(MemberInfo::getUserId, userInfo.getId()));
        if (CollectionUtil.isNotEmpty(memberInfos)) {
            for (MemberInfo memberInfo : memberInfos) {
                if (DateUtil.isIn(new Date(), DateUtil.parseDateTime(memberInfo.getStartDate()), DateUtil.parseDateTime(memberInfo.getEndDate()))) {
                    return R.ok(true);
                }
            }
        } else {
            return R.ok(false);
        }
        return R.ok(false);
    }

    /**
     * 订单支付回调
     *
     * @param orderCode 订单编号
     * @param userId    用户ID
     * @return 结果
     */
    @PostMapping("/editOrder")
    @Transactional(rollbackFor = Exception.class)
    public R editOrder(String orderCode, Integer userId) {
        // 获取用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));

        // 会员订单
        MemberOrderInfo memberRecordInfo = memberOrderInfoService.getOne(Wrappers.<MemberOrderInfo>lambdaQuery().eq(MemberOrderInfo::getCode, orderCode));

        // 会员信息
        RuleInfo ruleInfo = ruleInfoService.getById(memberRecordInfo.getType());

        memberRecordInfo.setStatus("1");
        memberRecordInfo.setStartDate(DateUtil.formatDateTime(new Date()));
        memberRecordInfo.setEndDate(DateUtil.formatDateTime(DateUtil.offsetDay(new Date(), ruleInfo.getDays())));
        memberOrderInfoService.updateById(memberRecordInfo);

        // 添加会员信息
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setUserId(userInfo.getId());
        memberInfo.setMemberLevel(ruleInfo.getId().toString());
        memberInfo.setPayDate(DateUtil.formatDateTime(new Date()));
        memberInfo.setStartDate(DateUtil.formatDateTime(new Date()));
        memberInfo.setEndDate(DateUtil.formatDateTime(DateUtil.offsetDay(new Date(), ruleInfo.getDays())));
        memberInfo.setPrice(memberRecordInfo.getPrice());
        memberInfoService.save(memberInfo);

        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setContent("恭喜您，会员订单支付成功！会员截至" + memberRecordInfo.getEndDate() + "！");
        notifyInfo.setStatus("0");
        notifyInfo.setUserId(userInfo.getId());
        notifyInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        notifyInfoService.save(notifyInfo);

        return R.ok(true);
    }
}
