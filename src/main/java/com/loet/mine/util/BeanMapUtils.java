package com.loet.mine.util;

import com.loet.annotation.MD5Field;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean工具类
 */
public class BeanMapUtils {

    /**
     * POJO转为MD5签名map
     *
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Map<String, String> bean2MD5ParamsMap(Object beanClass) throws Exception {
        Map<String, String> paramsMap = new HashMap<>();
        Field[] fields = beanClass.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 只要有MD5注解的属性
            if (field.isAnnotationPresent(MD5Field.class)) {
                // 属性名称
                String fieldName = field.getName();

                // 反射调用get方法获取属性值
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, beanClass.getClass());
                Method getMethod = pd.getReadMethod();
                String value = String.valueOf(getMethod.invoke(beanClass));

                paramsMap.put(fieldName, value);
            }
        }
        return paramsMap;
    }
}
