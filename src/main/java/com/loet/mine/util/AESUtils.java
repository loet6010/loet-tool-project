package com.loet.mine.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * @version V1.0
 * @desc AES 加密工具类
 */
public class AESUtils {
    private static final String IV_PARAMETER = "0123456789abcdef";
    private static final String ENCRYPT_PATTERN = "AES/CBC/PKCS5Padding";
    private static final String aesKey = PropertiesUtils.getString("aseSecretKey");

    private AESUtils() {
    }

    public static void main(String[] args) throws Exception {
        String baseUrl = "http://192.168.4.16:8082/crowdsourcing-cps/foreign/companyInfo";
        String encryptData = getAESEncryptData(10003L);
        System.out.println(encryptData);
        String url = baseUrl + "?signData=" + encryptData;
        System.out.println(url);
        String base64Decode = new String(Base64.decodeBase64(encryptData));
        System.out.println(base64Decode);
        String decryptString = decrypt(base64Decode);
        System.out.println(decryptString);
        String[] params = decryptString.split("\\|");
        for (String param : params) {
            System.out.println(param);
        }
    }

    /**
     * @param companyId
     * @return
     * @throws Exception
     */
    public static String getAESEncryptData(Long companyId) throws Exception {
        Long number = companyId * 2 / 5 + 35;
        String encryptCode = MD5Util.MD5Encode(String.valueOf(number));
        String params = String.format("companyId=%s|encryptCode=%s", companyId, encryptCode);
        String aesString = encrypt(params);

        return Base64.encodeBase64String(aesString.getBytes());
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        }

        Key key = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        byte[] raw = key.getEncoded();

        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(ENCRYPT_PATTERN);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        String originalString = new BASE64Encoder().encode(encrypted);

        // linux环境下会自动补\r \n到加密数据里面
        return originalString.replaceAll("[\r\n]", "");
    }

    /**
     * AES解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        }

        Key key = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        byte[] raw = key.getEncoded();

        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(ENCRYPT_PATTERN);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] decrypted = cipher.doFinal((new BASE64Decoder()).decodeBuffer(data));
        String originalString = new String(decrypted, StandardCharsets.UTF_8);

        // linux环境下会自动补\r \n到加密数据里面
        return originalString.replaceAll("[\r\n]", "");
    }
}
