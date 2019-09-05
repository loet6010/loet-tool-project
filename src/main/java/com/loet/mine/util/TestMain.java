package com.loet.mine.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @Author liurh
 * @Description TestMain
 * @Date 2019/9/3
 */
public class TestMain {
    public static void main(String[] args) throws Exception {

        long companyId = 10004L;

        System.out.println(getPartnerCode(companyId));

        System.out.println(getPartnerKey(companyId));
    }


    private static String getPartnerCode(long companyId) {
        String dateFormat = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String randomString = String.format("%05d", RandomUtils.nextInt(10, 100000));
        return String.format("%s%s%s", dateFormat, companyId, randomString);
    }

    private static String getPartnerKey(long companyId) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            long randomInt = companyId + RandomUtils.nextLong(100L, 100000L);
            String md5String = MD5Utils.encode(String.valueOf(randomInt));
            builder.append(md5String, 0, 5).append("_");
        }
        String key = builder.toString();
        key = key.substring(0, key.length() - 1);
        return key;
    }
}
