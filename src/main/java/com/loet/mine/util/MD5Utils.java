package com.loet.mine.util;

import com.loet.model.ApplyCashoutVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * MD5加密工具类
 */
public class MD5Utils {
    private static final String HEX_DIGITS[] =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private MD5Utils() {
    }

    /**
     * 获取大写MD5加密字符串
     *
     * @param origin
     * @return
     * @throws Exception
     */
    public static String encodeUpperCase(String origin) throws Exception {
        return encode(origin).toUpperCase();
    }

    /**
     * 获取小写MD5加密字符串
     *
     * @param origin
     * @return
     * @throws Exception
     */
    public static String encode(String origin) throws Exception {
        if (StringUtils.isBlank(origin)) {
            return null;
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] originBytes = origin.getBytes(StandardCharsets.UTF_8);
        byte[] digestBytes = md.digest(originBytes);
        return byteArrayToHexString(digestBytes);
    }

    /**
     * 参数MD5加密
     *
     * @param params
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String paramsMD5Sign(Map<String, String> params, String secretKey) throws Exception {
        if (params == null) {
            return "";
        }
        Map<String, String> sortedMap = new TreeMap<>(params);
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                continue;
            }
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
                str.append(entry.getKey());
                try {
                    str.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    str.append(entry.getValue());
                }
            }
        }
        if (secretKey != null) {
            str.append(secretKey);
        }

        return encode(str.toString());
    }

    /**
     * 参数MD5加密
     *
     * @param params      参数MAP
     * @param secretKey   签名KEY
     * @param joinSymbol  拼接符：key与value之间
     * @param unionSymbol 连接符：key与key之间
     * @return
     * @throws Exception
     */
    public static String paramsMD5Sign(Map<String, String> params, String secretKey, String joinSymbol,
            String unionSymbol) throws Exception {
        if (params == null) {
            return "";
        }
        Map<String, String> sortedMap = new TreeMap<>(params);
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                continue;
            }
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
                str.append(entry.getKey());
                if (StringUtils.isNotBlank(joinSymbol)) {
                    str.append(joinSymbol);
                }
                str.append(entry.getValue());
                if (StringUtils.isNotBlank(unionSymbol)) {
                    str.append(unionSymbol);
                }
            }
        }
        if (secretKey != null) {
            str.append(secretKey);
        }
        System.out.println(str.toString());

        return encode(str.toString());
    }

    /**
     * 二进制数组转为字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }

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
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static void main(String[] args) throws Exception {
        ApplyCashoutVo applyCashoutVo = new ApplyCashoutVo();
        applyCashoutVo.setCash(100L);
        applyCashoutVo.setCashoutPartnerCode("20180821250121");
        applyCashoutVo.setIdCard("362312199010213251");
        applyCashoutVo.setPhone("18765241520");
        applyCashoutVo.setUnixTime(System.currentTimeMillis());
        applyCashoutVo.setPartnerOrderId("order_id_0001");
        applyCashoutVo.setSign("");

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("partnerCode", "201909051000503169");
        paramsMap.put("name", "刘荣华");
        paramsMap.put("idCard", "362326198811142732");
        paramsMap.put("phone", "18768121960");
        paramsMap.put("accountType", "1");
        paramsMap.put("accountCard", "6225885215763749");

        System.out.println(paramsMap.toString());

        String sign = paramsMD5Sign(paramsMap, "6934d_b6094_73e47_9809d_3cd53", "=", "&");
        System.out.println(sign);
    }
}
