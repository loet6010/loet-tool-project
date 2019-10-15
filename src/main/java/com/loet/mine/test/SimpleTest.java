package com.loet.mine.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author liurh
 * @Description SimpleTest
 * @Date 2019/8/22
 */
public class SimpleTest {

    public static void main(String[] args) {
        String year = "2000";
        String month = "09";
        String day = "01";

        System.out.println(getAgeByBirth(year, month, day));
    }


    private static int getAgeByBirth(String year, String month, String day) {
        int age;
        try {
            String birthString = String.format("%s-%s-%s", year, month, day);
            System.out.println(birthString);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDay = format.parse(birthString);

            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthDay);

            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.MONTH) < birth.get(Calendar.MONTH)){
                    age = age - 1;
                } else if (now.get(Calendar.MONTH) == birth.get(Calendar.MONTH)){
                    if (now.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)){
                        age = age - 1;
                    }
                }
            }
            return age;
        } catch (Exception e) {
            return 0;
        }
    }
}
