package com.fank.f1k2.business.service;

import com.fank.f1k2.business.entity.WorkOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.business.entity.vo.ChatVo;

import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IWorkOrderInfoService extends IService<WorkOrderInfo> {

    /**
     * 回复
     *
     * @param content 回复内容
     * @param quotationId 工单ID
     * @param type 回复类型
     * @return 结果
     */
    boolean reply(String content, Integer quotationId, String type);

    /**
     * 根据工单ID查询回复
     *
     * @param quotationId 工单ID
     * @return 回复列表
     */
    List<ChatVo> queryReplyByQuotationId(Integer quotationId);
}
