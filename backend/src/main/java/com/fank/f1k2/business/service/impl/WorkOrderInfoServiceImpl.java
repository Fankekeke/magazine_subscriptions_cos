package com.fank.f1k2.business.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WorkOrderInfo;
import com.fank.f1k2.business.dao.WorkOrderInfoMapper;
import com.fank.f1k2.business.entity.vo.ChatVo;
import com.fank.f1k2.business.service.IWorkOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class WorkOrderInfoServiceImpl extends ServiceImpl<WorkOrderInfoMapper, WorkOrderInfo> implements IWorkOrderInfoService {

    /**
     * 回复
     *
     * @param content 回复内容
     * @param quotationId 工单ID
     * @param type 回复类型
     * @return 结果
     */
    @Override
    public boolean reply(String content, Integer quotationId, String type) {
        // 获取工单信息
        WorkOrderInfo workOrderInfo = this.getById(quotationId);
        ChatVo chatVo = new ChatVo();
        chatVo.setContent(content);
        chatVo.setCreateDate(DateUtil.formatDateTime(new Date()));
        chatVo.setType(type);
        if (StrUtil.isEmpty(workOrderInfo.getChatContent())) {
            String chatVoList = JSONUtil.toJsonStr(Collections.singletonList(chatVo));
            workOrderInfo.setChatContent(chatVoList);
            return this.updateById(workOrderInfo);
        } else {
            List<ChatVo> chatVoList = JSONUtil.toList(workOrderInfo.getChatContent(), ChatVo.class);
            chatVoList.add(chatVo);
            workOrderInfo.setChatContent(JSONUtil.toJsonStr(chatVoList));
            return this.updateById(workOrderInfo);
        }
    }

    /**
     * 根据工单ID查询回复
     *
     * @param quotationId 工单ID
     * @return 回复列表
     */
    @Override
    public List<ChatVo> queryReplyByQuotationId(Integer quotationId) {
        // 获取工单信息
        WorkOrderInfo workOrderInfo = this.getById(quotationId);
        if (StrUtil.isEmpty(workOrderInfo.getChatContent())) {
            return Collections.emptyList();
        }
        return JSONUtil.toList(workOrderInfo.getChatContent(), ChatVo.class);
    }

    /**
     * 分页获取工单信息
     *
     * @param page      分页对象
     * @param queryFrom 工单信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<WorkOrderInfo> page, WorkOrderInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
