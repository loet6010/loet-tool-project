package com.loet.mine.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 金额转大写工具类
 */
public class AmountUpperUtil {
    private static final String NUMBER_UPPER[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String DIGIT_UPPER[] = {"", "拾", "佰", "仟"};
    private static final String UNIT_UPPER[] = {"元", "万", "亿", "万亿"};

    public static void main(String[] args) {
        try {
            System.out.println(amountUppercase(9));
            System.out.println(amountUppercase(11));
            System.out.println(amountUppercase(50));
            System.out.println(amountUppercase(200));
            System.out.println(amountUppercase(301));
            System.out.println(amountUppercase(333333));
            System.out.println(amountUppercase(100000));
            System.out.println(amountUppercase(100100));
            System.out.println(amountUppercase(400210010));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 将金额（单位分）转为中文大写
     *
     * @param amount
     * @return
     */
    private static String amountUppercase(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("输入金额错误，必须是大于零的整数");
        }

        BigDecimal bigDecimal = new BigDecimal(amount);
        String strValue = String.valueOf(bigDecimal.toBigInteger());

        // 将金额分为整数和小数部分
        String head = "";
        String end = strValue;
        if (strValue.length() > 2) {
            head = strValue.substring(0, strValue.length() - 2);
            end = strValue.substring(strValue.length() - 2);
        }

        // 处理小数部分
        String endMoney = "";
        if ("00".equals(end)) {
            endMoney = "整";
        } else {
            if (end.length() > 1) {
                if (!end.substring(0, 1).equals("0")) {
                    endMoney += NUMBER_UPPER[Integer.valueOf(end.substring(0, 1))] + "角";
                } else if (end.substring(0, 1).equals("0") && !end.substring(1, 2).equals("0")) {
                    endMoney += "零";
                }
                if (!end.substring(1, 2).equals("0")) {
                    endMoney += NUMBER_UPPER[Integer.valueOf(end.substring(1, 2))] + "分";
                }
            } else {
                endMoney += NUMBER_UPPER[Integer.valueOf(end)] + "分";
            }
        }

        // 处理整数部分
        StringBuilder headMoney = new StringBuilder();
        char[] chars = head.toCharArray();
        Map<String, Boolean> map = new HashMap<>();
        boolean zeroKeepFlag = false;
        int victim = 0;
        for (int i = 0; i < chars.length; i++) {
            int idx = (chars.length - 1 - i) % 4;
            int vida = (chars.length - 1 - i) / 4;
            String s = NUMBER_UPPER[Integer.valueOf(String.valueOf(chars[i]))];
            if (!"零".equals(s)) {
                headMoney.append(s).append(DIGIT_UPPER[idx]).append(UNIT_UPPER[vida]);
                zeroKeepFlag = false;
            } else if (i != chars.length - 1 && map.get("zero" + vida) == null) {
                headMoney.append(s);
                zeroKeepFlag = true;
                map.put("zero" + vida, true);
            }
            if (victim != vida || i == chars.length - 1) {
                headMoney = new StringBuilder(headMoney.toString().replaceAll(UNIT_UPPER[vida], ""));
                headMoney.append(UNIT_UPPER[vida]);
            }
            if (zeroKeepFlag && (chars.length - 1 - i) % 4 == 0) {
                headMoney = new StringBuilder(headMoney.toString().replaceAll("零", ""));
            }
        }
        return headMoney + endMoney;
    }
}
