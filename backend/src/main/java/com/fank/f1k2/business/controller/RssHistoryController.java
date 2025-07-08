package com.fank.f1k2.business.controller;


import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.business.entity.RssHistory;
import com.fank.f1k2.business.service.IRssHistoryService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fanke
 */
@RestController
@RequestMapping("/cos/rss-history")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RssHistoryController {

    private final IRssHistoryService iRssHistoryService;

    /**
     * 分页查询
     * @param page
     * @param rssHistory
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R getRssHistoryByCodeAndPage(Page page, RssHistory rssHistory) {
        return R.ok(iRssHistoryService.page(page, Wrappers.<RssHistory>lambdaQuery()
                .eq(rssHistory.getRssName() != null && rssHistory.getRssName() != "",RssHistory::getRssName,rssHistory.getRssName())
                .eq(rssHistory.getTitle() != null && rssHistory.getTitle() != "",RssHistory::getTitle,rssHistory.getTitle())));
    }

    /**
     * 获取rss统计数据
     * @return
     */
    @GetMapping("/getRssStatByRssCode")
    @ApiOperation(value = "获取rss统计数据", notes = "获取rss统计数据")
    public R getRssStatByRssCode() {
        return R.ok(iRssHistoryService.getRssStatByRssCode());
    }

    /**
     * 添加新纪录
     * @param rssHistory
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加新纪录", notes = "添加新纪录")
    public R save(RssHistory rssHistory) {
        return R.ok(iRssHistoryService.save(rssHistory));
    }

    /**
     * 修改纪录
     * @param rssHistory
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改纪录", notes = "修改纪录")
    public R update(RssHistory rssHistory) {
        return R.ok(iRssHistoryService.updateById(rssHistory));
    }

    /**
     * 删除记录
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除记录", notes = "删除记录")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(iRssHistoryService.removeByIds(ids));
    }

}
