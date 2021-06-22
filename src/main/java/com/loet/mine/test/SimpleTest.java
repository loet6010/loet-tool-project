package com.loet.mine.test;

import org.springframework.util.FileCopyUtils;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @Author liurh
 * @Description SimpleTest
 * @Date 2019/8/22
 */
public class SimpleTest {
    private static byte[] PDF_BYTES = null;

    public static void main(String[] args) throws Exception {
        String mmsSign = UUID.randomUUID().toString().replace("-", "").toLowerCase();

        System.out.println(mmsSign);
    }

    private static String getPath(String path) {
        if (!path.startsWith("http://") && !path.startsWith("https://")) {
            path = "https://" + path;
        }
        return path;
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
        // return Base64Utils.encodeToString(PDF_BYTES);
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
            int len = -1;
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
