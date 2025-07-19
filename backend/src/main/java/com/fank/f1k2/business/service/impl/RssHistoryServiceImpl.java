package com.fank.f1k2.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.business.dao.RssHistoryMapper;
import com.fank.f1k2.business.entity.RssHistory;
import com.fank.f1k2.business.entity.RssInfo;
import com.fank.f1k2.business.service.IRssHistoryService;
import com.fank.f1k2.common.utils.RssParse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author fanke
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RssHistoryServiceImpl extends ServiceImpl<RssHistoryMapper, RssHistory> implements IRssHistoryService {

    private final RssHistoryMapper rssHistoryMapper;

    private final SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

    @Override
    public void parsRssList(List<RssInfo> rssInfoList) {
        rssInfoList.forEach(rssInfo -> {
            List<RssHistory> rssHistoryList = RssParse.parseRss(rssInfo.getRssUrl());
            // 倒序
            Collections.reverse(rssHistoryList);
            rssHistoryList.forEach(rssHistory -> {
                try {
                    rssHistory.setRssCode(rssInfo.getCode());
                    RssHistory rssHistoryMax = rssHistoryMapper.getMaxHistoryByDate(rssHistory);
                    // 判断发布日期是否大于库里最大日期
                    if(format0.parse(rssHistory.getPublishedDate()).getTime() > format0.parse(rssHistoryMax.getPublishedDate()).getTime()) {
                        rssHistory.setRssName(rssInfo.getRssName());
                        rssHistory.setPublishedDate(format0.format(sdf.parse(rssHistory.getPublishedDate())));
                        rssHistoryMapper.insert(rssHistory);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            });
        });
    }

    @Override
    public void parsRssInfo(RssInfo rssInfo) {
        RssHistory rssHistory1 = new RssHistory();
        List<RssHistory> rssHistoryList = RssParse.parseRss(rssInfo.getRssUrl());
        // 倒序
        Collections.reverse(rssHistoryList);
        rssHistoryList.forEach(rssHistory -> {
            try {
                RssHistory rssHistoryMax = rssHistoryMapper.getMaxHistoryByDate(rssHistory1);
                if(format0.parse(format0.format(sdf.parse(rssHistory.getPublishedDate()))).getTime() > format0.parse(rssHistoryMax.getPublishedDate()).getTime()) {
                    rssHistory.setPublishedDate(format0.format(sdf.parse(rssHistory.getPublishedDate())));
                    rssHistoryMapper.insert(rssHistory);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }
}
