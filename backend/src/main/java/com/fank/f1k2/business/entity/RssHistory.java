package com.fank.f1k2.business.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author fanke
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RssHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;
    /**
     * 所属rss
     */
    private String rssCode;

    /**
     * rss名称
     */
    private String rssName;

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


}
