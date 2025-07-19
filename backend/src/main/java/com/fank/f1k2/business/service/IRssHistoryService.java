package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.business.entity.RssHistory;
import com.fank.f1k2.business.entity.RssInfo;

import java.util.List;

/**
 * @author fanke
 */
public interface IRssHistoryService extends IService<RssHistory> {

    /**
     * 解析Rss列表 添加新记录
     * @param rssInfoList
     */
    void parsRssList(List<RssInfo> rssInfoList);

    /**
     * 解析rss
     * @param rssInfo
     */
    void parsRssInfo(RssInfo rssInfo);

}
