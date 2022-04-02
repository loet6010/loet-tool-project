package com.loet.mine.test;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：StreamTraceTest
 *
 * @author 罗锅
 * @date 2022/1/24 14:02
 */
public class StreamTraceTest {

    public static void main(String[] args) {
        List<String> testList = new ArrayList<>();
        testList.add("zhang_san");
        testList.add("");
        testList.add("li_si");

        List<String> collect = testList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        System.out.printf(collect.toString());
    }
}
