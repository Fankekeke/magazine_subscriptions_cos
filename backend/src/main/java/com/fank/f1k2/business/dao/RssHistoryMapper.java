package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fank.f1k2.business.entity.RssHistory;
import org.apache.ibatis.annotations.Param;

/**
 * @author fanke
 */
public interface RssHistoryMapper extends BaseMapper<RssHistory> {

    // 获取库里最新消息
    RssHistory getMaxHistoryByDate(@Param("rssHistory") RssHistory rssHistory);
}
