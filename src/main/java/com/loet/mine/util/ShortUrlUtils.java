package com.loet.mine.util;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;

import java.nio.charset.StandardCharsets;

public class ShortUrlUtils {
    private static final String URL_START_SIMPLE = "http";
    private static final String URL_START = "https://";
    private static final String URL_SEPARATOR = "?";

    private ShortUrlUtils() {
    }

    public static String joinParams(String url, String appCode, Long id) {
        if (!url.startsWith(URL_START_SIMPLE)) {
            url = URL_START + url;
        }

        // 拼商户号
        if (!url.contains(URL_SEPARATOR)) {
            url = url + URL_SEPARATOR + "mi=" + id;
        } else {
            url = url + "&mi=" + id;
        }

        // 拼页面标识(即 appCode)
        url = url + "&ac=" + appCode;

        return url;
    }

    /**
     * 获取短链接ID
     *
     * @param url
     * @return
     */
    public static String getShortId(String url) {
        String id = null;
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (urlValidator.isValid(url)) {
            url = url + System.currentTimeMillis();
            id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
        }
        return id;
    }

    public static void main(String[] args) {
        String url = "mp.weixin.qq.com/s/HtJxvgmESSQZ5fW_x4TLWw?a=123";
        String paramsUrl = joinParams(url, "app-1", 1L);
        System.out.println(paramsUrl);
    }
}
