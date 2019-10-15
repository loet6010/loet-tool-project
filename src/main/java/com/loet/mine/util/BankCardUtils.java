package com.loet.mine.util;

/**
 * 银行工具类
 */
public class BankCardUtils {

    /**
     * 匹配Luhn算法：可用于检测银行卡卡号
     *
     * @param cardNo
     * @return
     */
    public static boolean matchLuhn(String cardNo) {
        char[] strArray = cardNo.toCharArray();
        int n = strArray.length;
        int sum = 0;
        for (int i = n; i >= 1; i--) {
            int a = strArray[n - i] - '0';
            // 偶数位乘以2
            if (i % 2 == 0) {
                a *= 2;
            }
            // 十位数和个位数相加，如果不是偶数位，不乘以2，则十位数为0
            sum = sum + a / 10 + a % 10;
        }

        return sum % 10 == 0;
    }

    public static void main(String[] args) {
        String cardNo = "6253624033100189";

        System.out.println(matchLuhn(cardNo));
    }
}
