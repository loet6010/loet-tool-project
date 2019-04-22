package com.loet.mine.util;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/**
 * 加密工具类
 */
public class EncryptUtils {
    private static final String HEX_DIGITS[] =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static final String IV_PARAMETER = "0123456789abcdef";
    private static final String ENCRYPT_PATTERN = "AES/CBC/PKCS5Padding";
    private static final String DECRYPT_PATTERN = "AES/CBC/NoPadding";

    private EncryptUtils() {
    }

    /**
     * AES加密
     *
     * @param aesKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String AESEncrypt(String aesKey, String data) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        }

        Key key = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        byte[] raw = key.getEncoded();

        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(ENCRYPT_PATTERN);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
        cipher.init(1, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return (new BASE64Encoder()).encode(encrypted);
    }

    /**
     * AES解密
     *
     * @param aesKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String AESDecrypt(String aesKey, String data) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        }

        Key key = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        byte[] raw = key.getEncoded();

        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(DECRYPT_PATTERN);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
        cipher.init(2, secretKeySpec, iv);
        byte[] decrypted = cipher.doFinal((new BASE64Decoder()).decodeBuffer(data));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 获取大写MD5加密字符串
     *
     * @param origin
     * @return
     * @throws Exception
     */
    public static String MD5EncodeUpperCase(String origin) throws Exception {
        return MD5Encode(origin).toUpperCase();
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

        return MD5Encode(str.toString());
    }

    /**
     * 获取小写MD5加密字符串
     *
     * @param origin
     * @return
     * @throws Exception
     */
    private static String MD5Encode(String origin) throws Exception {
        if (StringUtils.isBlank(origin)) {
            return null;
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] originBytes = origin.getBytes(StandardCharsets.UTF_8);
        byte[] digestBytes = md.digest(originBytes);
        return byteArrayToHexString(digestBytes);
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
}
