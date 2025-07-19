package com.fank.f1k2.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.BookDetailInfo;
import com.fank.f1k2.business.entity.BookInfo;
import com.fank.f1k2.business.entity.RssHistory;
import com.fank.f1k2.business.entity.RssInfo;
import com.fank.f1k2.business.service.IBookDetailInfoService;
import com.fank.f1k2.business.service.IBookInfoService;
import com.fank.f1k2.business.service.IRssHistoryService;
import com.fank.f1k2.business.service.IRssInfoService;
import com.fank.f1k2.common.utils.R;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fanke
 */
@RestController
@RequestMapping("/cos/rss-history")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RssHistoryController {

    private final IRssHistoryService iRssHistoryService;

    private final IRssInfoService iRssInfoService;

    private final IBookInfoService bookInfoService;

    private final IBookDetailInfoService bookDetailService;

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
                .eq(rssHistory.getRssName() != null,RssHistory::getRssName,rssHistory.getRssName())
                .eq(rssHistory.getTitle() != null,RssHistory::getTitle,rssHistory.getTitle())));
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

    @GetMapping("/setRssInfo")
    public R setRssInfo() {
        List<RssInfo> rssInfoList = iRssInfoService.list();
        List<BookInfo> bookInfoList = new ArrayList<>();

        for (RssInfo rssInfo : rssInfoList) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setCode(rssInfo.getCode());
            bookInfo.setName(rssInfo.getRssName());
            bookInfo.setRssUrl(rssInfo.getRssUrl());
            bookInfo.setRssAuthor(rssInfo.getRssAuthor());
            bookInfo.setRssType(rssInfo.getRssType());

            bookInfo.setStatus("1");
            bookInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            bookInfoList.add(bookInfo);
        }
        bookInfoService.saveBatch(bookInfoList);
        return R.ok();
    }

    @GetMapping("/setRssHistory")
    public R setRssHistory() {
        List<RssHistory> rssHistoryList = iRssHistoryService.list();
        List<BookDetailInfo> bookHistoryList = new ArrayList<>();

        for (RssHistory rssHistory : rssHistoryList) {
            BookDetailInfo bookDetailInfo = BeanUtil.copyProperties(rssHistory, BookDetailInfo.class);
            bookDetailInfo.setBookId(rssHistory.getRssCode());
            bookDetailInfo.setViews(0);
            bookDetailInfo.setCheckFlag("0");
            bookDetailInfo.setContent(rssHistory.getValue());
            bookDetailInfo.setValue(null);
            bookDetailInfo.setName(rssHistory.getTitle());
            bookDetailInfo.setWords(StrUtil.length(rssHistory.getValue()));
            bookHistoryList.add(bookDetailInfo);
        }
        return R.ok(bookDetailService.saveBatch(bookHistoryList));
    }
}
