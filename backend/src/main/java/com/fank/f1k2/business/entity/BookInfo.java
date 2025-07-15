package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

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
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订阅源ID
     */
    private String code;

    /**
     * 订阅源名称
     */
    private String name;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 最后更新时间
     */
    private String updateDate;

    /**
     * 最后内容
     */
    private String lastChapter;

    /**
     * 标签
     */
    private String tag;

    /**
     * 作品介绍
     */
    private String content;

    /**
     * 订阅源图片
     */
    private String images;

    /**
     * 作品分类
     */
    private String type;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 审核状态（0.审核中 1.通过 2.驳回）
     */
    private String status;

    /**
     * rss地址
     */
    private String rssUrl;

    /**
     * rss作者
     */
    private String rssAuthor;

    /**
     * rss类型
     */
    private String rssType;

    /**
     * 订阅价格
     */
    private BigDecimal price;

    @TableField(exist = false)
    private String authorName;

    @TableField(exist = false)
    private Integer views;

    @TableField(exist = false)
    private AuthorInfo authorInfo;

    @TableField(exist = false)
    private Integer userId;

}
