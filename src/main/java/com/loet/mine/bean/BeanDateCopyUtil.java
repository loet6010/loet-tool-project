package com.loet.mine.bean;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * 支持date类型的Bean拷贝
 * 
 * @Description BeanDateCopyUtil
 * @author liurh
 * @date 2018年6月12日
 */
public class BeanDateCopyUtil extends BeanUtils {

    static {
        ConvertUtils.register(new BeanDateConvert(), java.util.Date.class);
        ConvertUtils.register(new BeanDateConvert(), String.class);
    }

    /**
     * 构造方法私有，禁止实例化
     * 
     * @throws InstantiationException
     */
    private BeanDateCopyUtil() throws InstantiationException {
        throw new InstantiationException();
    }

    /**
     * Bean支持对日期copy
     *
     * @param target
     * @param source
     */
    public static void copyProperties(Object target, Object source) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Bean拷贝转换错误：" + e.getMessage());
        }
    }
}
