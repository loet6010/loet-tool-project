package com.loet.mine.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.util.FileCopyUtils;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author liurh
 * @Description SimpleTest
 * @Date 2019/8/22
 */
public class SimpleTest {
    private static final int HASH_BITS = 0x7fffffff;
    private static byte[] PDF_BYTES = null;

    private static final String header_bearer = "Bearer\n";

    public static void main(String[] args) throws Exception {
        Calendar calendar = Calendar.getInstance();
        System.out.println(DateUtil.format(calendar.getTime(), DatePattern.NORM_DATETIME_FORMAT));
        calendar.add(Calendar.DATE, 1000);
        System.out.println(DateUtil.format(calendar.getTime(), DatePattern.NORM_DATETIME_FORMAT));

        String header = header_bearer + "acdtest";
        System.out.println(header);

        test();
    }

    private static void test() {
        List<String> names = new ArrayList<String>() {{
            add("Hello");
            add("World");
            add("Good");
        }};
        Iterator iterator = names.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("Hello")) {
                iterator.remove();
            }
        }
    }

    private static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    private static String getPdfBytes(String templateUrl) throws Exception {
        if (PDF_BYTES == null || PDF_BYTES.length <= 0) {
            URL url = new URL(templateUrl);
            InputStream inputStream = url.openConnection().getInputStream();
            PDF_BYTES = FileCopyUtils.copyToByteArray(inputStream);
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(PDF_BYTES);
    }

    public static String getFileBase64(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
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
                if (now.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
                    age = age - 1;
                } else if (now.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {
                    if (now.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
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
