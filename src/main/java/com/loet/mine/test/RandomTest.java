package com.loet.mine.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：RandomTest
 *
 * @author 罗锅
 * @date 2021/8/5 17:39
 */
public class RandomTest {
    private static final ExecutorService executorService = new ThreadPoolExecutor(5, 100, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100), new ThreadFactoryBuilder().setNameFormat("byai-notify-pool-%02d").build());

    private static final int n = 77;
    private static final int e = 13;
    private static final int d = 37;

    public static void main(String[] args) {
        for (int i = 0; i <100; i++) {
            Long millis = System.currentTimeMillis();
            Integer nextInt = RandomUtils.nextInt(10000, 99999);
            String name = "zx" + millis + nextInt;
            System.out.println(i + "_" + millis + "_" + nextInt + "_" + name);
        }
    }

    private static BigDecimal encodePublic(int m) {
        BigDecimal me = BigDecimal.valueOf(1L);
        for (int i = 0; i < e; i++) {
            me = me.multiply(BigDecimal.valueOf(m));
        }
        System.out.println(me);

        return me.divideAndRemainder(BigDecimal.valueOf(n))[1];
    }

    private static BigDecimal decodePublic(BigDecimal c) {
        BigDecimal cd = BigDecimal.valueOf(1L);
        for (int i = 0; i < d; i++) {
            cd = cd.multiply(c);
        }
        System.out.println(cd);

        return cd.divideAndRemainder(BigDecimal.valueOf(n))[1];
    }

    private static BigDecimal encodePrivate(int m) {
        BigDecimal md = BigDecimal.valueOf(1L);
        for (int i = 0; i < d; i++) {
            md = md.multiply(BigDecimal.valueOf(m));
        }
        System.out.println(md);

        return md.divideAndRemainder(BigDecimal.valueOf(n))[1];
    }

    private static BigDecimal decodePrivate(BigDecimal c) {
        BigDecimal ce = BigDecimal.valueOf(1L);
        for (int i = 0; i < e; i++) {
            ce = ce.multiply(c);
        }
        System.out.println(ce);

        return ce.divideAndRemainder(BigDecimal.valueOf(n))[1];
    }
}
