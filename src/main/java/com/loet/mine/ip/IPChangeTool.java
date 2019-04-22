package com.loet.mine.ip;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * IP和十进制数转化
 * 
 * @Description IPChangeTool
 * @author liurh
 * @date 2018年7月27日
 */
public class IPChangeTool {

    /**
     * main方法，输入IP或数字转化
     *
     * @param args
     */
    public static void main(String[] args) {
         String HEX_DIGITS[] =
                {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        String IV_PARAMETER = "0123456789abcdef";

        byte[] bytesOne = Arrays.toString(HEX_DIGITS).getBytes(StandardCharsets.UTF_8);
        byte[] bytesTwo = IV_PARAMETER.getBytes(StandardCharsets.UTF_8);

        System.out.println(Arrays.toString(bytesOne));
        System.out.println(Arrays.toString(bytesTwo));
    }

    /**
     * 从十进制数获取IP地址
     *
     * @param num
     * @return
     */
    public static String getIpFromNum(long num) {
        StringBuilder ipBuilder = new StringBuilder();
        String binaryIp = Long.toBinaryString(num);

        if (binaryIp.length() > 32) {
            return null;
        }

        binaryIp = getSupplementZero(binaryIp, 32);

        for (int i = 0; i < 4; i++) {
            String binarySubIp = binaryIp.substring(i * 8, (i + 1) * 8);
            ipBuilder.append(Integer.valueOf(binarySubIp, 2)).append(".");
        }

        return ipBuilder.toString().substring(0, ipBuilder.toString().length() - 1);
    }

    /**
     * 从IP地址获取十进制数
     *
     * @param ip
     * @return
     */
    private static long getNumFromIp(String ip) {
        StringBuilder ipBuilder = new StringBuilder();

        String[] subIps = ip.split("\\.");
        for (String subIp : subIps) {
            String binarySubIp = Integer.toBinaryString(Integer.parseInt(subIp));
            binarySubIp = getSupplementZero(binarySubIp, 8);

            ipBuilder.append(binarySubIp);
        }

        return Long.valueOf(ipBuilder.toString(), 2);
    }

    /**
     * 按给定位数补0
     *
     * @param sourceString
     * @param digits
     * @return
     */
    private static String getSupplementZero(String sourceString, int digits) {
        int surplusNum = (digits - sourceString.length());
        String supplementZero = "";
        if (surplusNum > 0) {
            supplementZero = String.format("%0" + surplusNum + "d", 0);
        }
        sourceString = supplementZero + sourceString;

        return sourceString;
    }

}
