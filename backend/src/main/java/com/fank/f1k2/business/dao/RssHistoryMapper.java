package com.fank.f1k2.business.dao;

import com.fank.f1k2.business.entity.RssHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author fanke
 */
public interface RssHistoryMapper extends BaseMapper<RssHistory> {

    // 获取库里最新消息
    RssHistory getMaxHistoryByDate(@Param("rssHistory") RssHistory rssHistory);

    // 获取rss统计数据
    List<Map> getRssStatByRssCode();
}
