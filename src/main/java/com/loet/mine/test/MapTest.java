package com.loet.mine.test;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 描述：MapTest
 *
 * @author 罗锅
 * @date 2023/11/24
 */
public class MapTest {
    public static void main(String[] args) {
        // 初始化map容量
        Map<String, String> testMap = MapUtil.newHashMap(5);
        Map<String, String> testMap2 = Maps.newHashMapWithExpectedSize(5);
    }
}
