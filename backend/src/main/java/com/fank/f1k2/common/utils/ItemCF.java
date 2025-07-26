package com.fank.f1k2.common.utils;



import com.fank.f1k2.business.entity.RelateDTO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 核心算法
 *
 * @since JDK1.8
 */
public class ItemCF {

    /**
     * 方法描述: 推荐列表
     *
     * @param itemId 当前电影id
     * @param list   用户电影评分数据
     * @return {@link List<Integer>}
     */
    public static List<Integer> recommend(Integer itemId, List<RelateDTO> list) {
        //按内容分组
        Map<Integer, List<RelateDTO>> itemMap = list.stream().collect(Collectors.groupingBy(RelateDTO::getItemId));
        //获取其他内容与当前内容的关系值
        Map<Integer, Double> itemDisMap = CoreMath.computeNeighbor(itemId, itemMap, 1);
        //获取关系最近内容
        double maxValue = Collections.max(itemDisMap.values());
        return itemDisMap.entrySet().stream().filter(e -> e.getValue() == maxValue || e.getValue() >= 0.0).map(Map.Entry::getKey).collect(Collectors.toList());
    }


}
