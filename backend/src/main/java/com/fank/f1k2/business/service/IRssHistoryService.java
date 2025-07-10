package com.fank.f1k2.business.service;

import com.fank.f1k2.business.entity.RssHistory;
import com.fank.f1k2.business.entity.RssInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author fanke
 */
public interface IRssHistoryService extends IService<RssHistory> {

    /**
     * 解析Rss列表 添加新记录
     *
     * @param rssInfoList
     */
    void parsRssList(List<RssInfo> rssInfoList);

    /**
     * 解析rss
     *
     * @param rssInfo
     */
    void parsRssInfo(RssInfo rssInfo);

    /**
     * 获取rss统计数据
     *
     * @return 结果
     */
    List<Map> getRssStatByRssCode();


}
