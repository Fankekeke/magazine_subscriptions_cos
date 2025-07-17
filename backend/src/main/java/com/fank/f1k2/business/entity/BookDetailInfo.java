package com.fank.f1k2.business.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订阅源内容管理
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BookDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属订阅源
     */
    private String bookId;

    /**
     * 内容名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer indexNo;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 字数
     */
    private Integer words;

    /**
     * 是否需要会员（0.否 1.是）
     */
    private String checkFlag;

    /**
     * 发布时间
     */
    private String createDate;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 图片列表
     */
    private String webImg;

    /**
     * 作者
     */
    private String author;

    /**
     * 链接
     */
    private String link;

    /**
     * 上传时间
     */
    private String publishedDate;

    /**
     * 流媒体播放文件列表
     */
    private String video;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类
     */
    private String type;

    /**
     * 简介
     */
    private String value;

    @TableField(exist = false)
    private String bookName;

    @TableField(exist = false)
    private String authorName;

    @TableField(exist = false)
    private Integer userId;

}
