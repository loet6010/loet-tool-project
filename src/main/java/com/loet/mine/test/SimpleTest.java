package com.loet.mine.test;

/**
 * @Author liurh
 * @Description SimpleTest
 * @Date 2019/8/22
 */
public class SimpleTest {

    public static void main(String[] args) {
        String fileName = "my.test.png";
        int index = fileName.lastIndexOf(".");
        System.out.println(index);
        String suffix;
        if (index != -1) {
            suffix = fileName.substring(index);
        } else {
            suffix = ".jpg";
        }
        System.out.println(suffix);
    }
}
