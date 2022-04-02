package com.loet.mine.util;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：变量工具类
 *
 * @author 罗锅
 * @date 2022/1/15 9:20
 */
public class VariableUtil {
    /**
     * 变量关键字匹配正则
     */
    public static final String TEMPLATE_REGEX = "[{][{][^}]+(.DATA}})";

    public static void main(String[] args) {
        String contentJson = "{\"content\":\"{{first.DATA}}\\n\\n退款原因：{{reason.DATA}}\\n退款金额：{{refund" +
                ".DATA}}\\n{{remark.DATA}}\",\"messageType\":1," +
                "\"templateId\":\"C8m30W8jPZSJ5yrSCQrpGgWcpXdv-BTT2NrQNQQnEk4\"}";
        String variableParams = "[\"这是变量1\",\"这是变量2\",\"这是变量3\",\"这是变量4\",\"这是变量5\"]";

        System.out.println(variableParams);
        String replaceContentJson = replaceVariableByIndex(contentJson, variableParams);
        System.out.println(contentJson);
        System.out.println(replaceContentJson);
    }

    /**
     * 按顺序替换变量
     * <br/>变量数量不够则为空
     *
     * @param contentJson
     * @param variableParams
     * @return
     */
    public static String replaceVariableByIndex(String contentJson, String variableParams) {
        // 无内容或变量则直接返回
        if (StringUtils.isBlank(contentJson) || StringUtils.isBlank(variableParams)) {
            return contentJson;
        }
        List<String> paramsList = JSON.parseArray(variableParams, String.class);
        if (CollUtil.isEmpty(paramsList)) {
            return contentJson;
        }

        // 用正则匹配变量然后循环替换
        Pattern pattern = Pattern.compile(VariableUtil.TEMPLATE_REGEX);
        Matcher matcher = pattern.matcher(contentJson);
        int i = 0;
        while (matcher.find()) {
            String replaceParam = "";
            if (i <= paramsList.size() - 1) {
                replaceParam = paramsList.get(i++);
            }

            contentJson = matcher.replaceFirst(replaceParam);
            matcher = pattern.matcher(contentJson);
        }

        return contentJson;
    }
}
