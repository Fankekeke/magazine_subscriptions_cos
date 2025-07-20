package com.fank.f1k2.business.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 工单信息
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 消息内容
     */
    private String content;

    /**
     * 状态（0.进行中 1.已完成）
     */
    private String status;

    /**
     * 所属人
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 已读时间
     */
    private String finishDate;

    /**
     * 工单内容
     */
    private String chatContent;

    @TableField(exist = false)
    private String userName;


}
