package com.loet.constant;

/**
 * 平台常量参数
 * 
 * @Description ConstantUtil
 * @author liurh
 * @date 2018年6月4日
 */
public class Constants {
    // UTF-8字符集编码
    public static final String UTF8 = "UTF-8";
    // 字符串"1"
    public static final String STRING_ONE = "1";
    // 字符串"0"
    public static final String STRING_ZERO = "0";
    // token缓存有效小时数
    public static final int CACHE_HOUR = 24;
    // token缓存最大个数
    public static final int MAXIMUM_SIZE = 100;
    // 日期时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    // 日期时间格式正则表达式：yyyy-MM-dd HH:mm:ss
    public static final String DATE_TIME_REGEXP = "([0-9]{4})-([0-1][0-9])-([0-3][0-9])[\\s\\S]([0-2][0-9]):([0-6][0-9]):([0-6][0-9])";
    // 日期格式正则表达式：yyyy-MM-dd
    public static final String DATE_REGEXP = "([0-9]{4})-([0-1][0-9])-([0-3][0-9])";
    // 一个空格
    public static final String BLANK_ONE = " ";
    // 开始时间
    public static final String TIME_BEGIN = "00:00:00";
    // 结束时间
    public static final String TIME_END = "23:59:59";
    // 百分比号
    public static final String PERCENT = "%";
}
