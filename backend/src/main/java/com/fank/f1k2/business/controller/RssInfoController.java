package com.fank.f1k2.business.controller;


import com.fank.f1k2.common.utils.R;
import com.fank.f1k2.common.utils.RssParse;
import com.fank.f1k2.business.entity.RssInfo;
import com.fank.f1k2.business.service.IRssHistoryService;
import com.fank.f1k2.business.service.IRssInfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author fanke
 */
@RestController
@RequestMapping("/cos/rss-info")
@AllArgsConstructor
public class RssInfoController {

    private final IRssInfoService iRssInfoService;

    private final IRssHistoryService iRssHistoryService;

    /**
     * 分页查询
     * @param page
     * @param rssInfo
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R getRssInfoByPage(Page page, RssInfo rssInfo) {
        return R.ok(iRssInfoService.page(page, Wrappers.<RssInfo>lambdaQuery()
                .like(rssInfo.getRssName()!=null,RssInfo::getRssName,rssInfo.getRssName())
                .like(rssInfo.getRssAuthor()!=null,RssInfo::getRssAuthor,rssInfo.getRssAuthor())));
    }

    /**
     * 解析Rss
     * @param rssUrl
     * @return
     */
    @GetMapping("/parsing")
    @ApiOperation(value = "解析Rss", notes = "解析Rss")
    public R parsingRss(@RequestParam String rssUrl) {
        RssInfo rssInfo = new RssInfo();
        rssInfo.setRssUrl(rssUrl);
        // iRssHistoryService.parsRssInfo(rssInfo);
        return R.ok(RssParse.parseRss(rssUrl));
    }

    /**
     * 查询全部
     * @param rssInfo
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询全部", notes = "查询全部")
    public R getRssInfoByList(RssInfo rssInfo) {
        return R.ok(iRssInfoService.list(Wrappers.<RssInfo>lambdaQuery()
                .like(rssInfo.getRssName()!=null,RssInfo::getRssName,rssInfo.getRssName())
                .like(rssInfo.getRssAuthor()!=null,RssInfo::getRssAuthor,rssInfo.getRssAuthor())));
    }

    /**
     * 添加Rss
     * @param rssInfo
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加Rss", notes = "添加Rss")
    public R save(RssInfo rssInfo) {
        rssInfo.setCode(UUID.randomUUID().toString());
        return R.ok(iRssInfoService.save(rssInfo));
    }

    /**
     * 修改Rss
     * @param rssInfo
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改Rss", notes = "修改Rss")
    public R update(RssInfo rssInfo) {
        return R.ok(iRssInfoService.updateById(rssInfo));
    }

    /**
     * 删除Rss
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除Rss", notes = "删除Rss")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(iRssInfoService.removeByIds(ids));
    }

}
