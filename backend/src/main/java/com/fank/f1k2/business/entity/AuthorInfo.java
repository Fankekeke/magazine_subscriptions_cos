package com.fank.f1k2.business.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订阅源管理
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订阅源编号
     */
    private String code;

    /**
     * 订阅源名称
     */
    private String name;

    /**
     * 备注
     */
    private String content;

    /**
     * 头像
     */
    private String images;

    /**
     * 性别（1.男 2.女）
     */
    private String sex;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 粉丝数量
     */
    @TableField(exist = false)
    private Integer fansNum;

    @TableField(exist = false)
    private List<BookInfo> bookInfoList;
}
