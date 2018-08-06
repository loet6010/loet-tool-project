package com.loet.mine.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5工具类
 * 
 * @Description MD5Util
 * @author liurh
 * @date 2018年6月12日
 */
public class MD5Util {

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    /**
     * 构造方法私有，禁止实例化
     * 
     * @throws InstantiationException
     */
    private MD5Util() throws InstantiationException {
        throw new InstantiationException();
    }

    /**
     * 获取大写MD5加密字符串
     *
     * @param origin
     * @return
     */
    public static String MD5EncodeUpperCase(String origin) {
        return MD5Encode(origin).toUpperCase();
    }

    /**
     * 获取小写MD5加密字符串
     *
     * @param origin
     * @return
     */
    private static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ignored) {
        }
        return resultString;
    }

    /**
     * 二进制数组转为字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) resultSb.append(byteToHexString(aB));

        return resultSb.toString();
    }

    /**
     * 二进制数转为字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

}
