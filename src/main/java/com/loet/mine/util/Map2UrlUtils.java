package com.loet.mine.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 描述：Map2UrlUtils
 *
 * @author 罗锅
 * @date 2023/9/6
 */
public class Map2UrlUtils {
    private Map2UrlUtils() {
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> params = new TreeMap<>();
        params.put("userId", "123456");
        params.put("name", URLEncoder.encode("张三", "gbk"));
        params.put("age", "");
        params.put("address", URLEncoder.encode("西湖区", "gbk"));

        String baseUrl = "www.test.com";

        String url = map2Url(baseUrl, params);
        System.out.println(url);

        int index = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (++index == params.size()) {
                stringAppend(stringBuilder, entry.getKey(), entry.getValue(), true);
            } else {
                stringAppend(stringBuilder, entry.getKey(), entry.getValue());
            }
        }
        System.out.println(stringBuilder.toString());
    }

    private static void stringAppend(StringBuilder stringBuilder, String key, String value) {
        stringAppend(stringBuilder, key, value, false);
    }

    private static void stringAppend(StringBuilder stringBuilder, String key, String value, boolean isLastKey) {
        stringBuilder.append(key).append("=");
        if (StringUtils.isNotBlank(value)) {
            stringBuilder.append(value);
        }
        if (!isLastKey) {
            stringBuilder.append("$");
        }
    }

    /**
     * map参数转URL
     *
     * @param baseUrl
     * @param params
     * @return
     */
    public static String map2Url(String baseUrl, Map<String, String> params) {
        return map2Url(baseUrl, params, true);
    }

    /**
     * map参数转URL
     *
     * @param baseUrl
     * @param params
     * @param needBlankValue
     * @return
     */
    public static String map2Url(String baseUrl, Map<String, String> params, boolean needBlankValue) {
        if (CollectionUtils.isEmpty(params)) {
            return baseUrl;
        }

        StringBuilder paramBuilder = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (index > 0) {
                paramBuilder.append("&");
            }

            if (needBlankValue || StringUtils.isNotBlank(entry.getValue())) {
                paramBuilder.append(entry.getKey()).append("=");
                if (entry.getValue() != null) {
                    paramBuilder.append(entry.getValue());
                }
            }

            index++;
        }

        return baseUrl + "?" + paramBuilder;
    }
}
