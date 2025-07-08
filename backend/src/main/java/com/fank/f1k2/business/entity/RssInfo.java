package com.fank.f1k2.business.entity;

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
public class RssInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;
    /**
     * rss编号
     */
    private String code;

    /**
     * rss名称
     */
    private String rssName;

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


}
