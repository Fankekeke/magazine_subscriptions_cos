package com.fank.f1k2.business.controller;


import com.fank.f1k2.common.service.CacheService;
import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.business.entity.UserInfo;
import com.fank.f1k2.business.service.IUserInfoService;
import com.fank.f1k2.system.domain.User;
import com.fank.f1k2.system.service.UserService;
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
@RequestMapping("/cos/user-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoController {

    private final IUserInfoService userInfoService;

    private final UserService userService;

    private final CacheService cacheService;

    /**
     * 分页获取用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserInfo> page, UserInfo userInfo) {
        return R.ok(userInfoService.selectUserPage(page, userInfo));
    }

    /**
     * 用户信息详情
     *
     * @param id 用户ID
     * @return 结果
     */
    @GetMapping("/detail/{userId}")
    public R userDetail(@PathVariable("userId") Integer id) {
        return R.ok(userInfoService.userDetail(id));
    }

    /**
     * 用户状态更改
     *
     * @param flag   状态
     * @param userId 用户ID
     * @return 结果
     */
    @PostMapping("/user/audit")
    public R userStatusAudit(Integer flag, Integer userId) throws Exception {
        UserInfo userInfo = userInfoService.getById(userId);
        userInfo.setStatus(flag.toString());
        userInfoService.updateById(userInfo);

        userService.update(Wrappers.<User>lambdaUpdate().set(User::getStatus, flag).eq(User::getUserId, userInfo.getUserId()));
        User user = userService.getById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }
        // 重新将用户信息，用户角色信息，用户权限信息 加载到 redis中
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());
        cacheService.savePermissions(user.getUsername());
        return R.ok(true);
    }

    /**
     * 用户信息详情
     *
     * @param id 用户ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(userInfoService.getById(id));
    }

    /**
     * 用户信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(userInfoService.list());
    }

    /**
     * 新增用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PostMapping
    public R save(UserInfo userInfo) {
        userInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(userInfoService.save(userInfo));
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 删除用户信息
     *
     * @param ids ids
     * @return 用户信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userInfoService.removeByIds(ids));
    }
}
