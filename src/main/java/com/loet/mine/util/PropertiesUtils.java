package com.loet.mine.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态加载properties文件,修改文件无需重新启动项目
 */
public class PropertiesUtils {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    private static final String MAIN_CONF_FILE = "conf/application.properties";
    private static PropertiesUtils instance = new PropertiesUtils();
    private static PropertiesConfiguration propertiesConfiguration;

    /**
     * 私有构造方法, 阻止应用直接通过new 生成类实例， 而只能通过getInstance()获得类实例
     *
     * @throws ConfigurationException
     */
    private PropertiesUtils() {
        PropertiesConfiguration pc = new PropertiesConfiguration();
        pc.setEncoding("UTF-8");
        try {
            pc.load(MAIN_CONF_FILE);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            logger.error("can't load the MAIN CONFIG FILE " + MAIN_CONF_FILE);
        }

        propertiesConfiguration = pc;
    }

    /**
     * 获得PropertiesUtils的静态实例
     *
     * @return PropertiesUtils的静态实例
     */
    private static PropertiesUtils getInstance() {
        return instance;
    }

    private PropertiesConfiguration getPropertiesConfiguration() {
        return propertiesConfiguration;
    }

    /**
     * 获取配置文件内容
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        PropertiesConfiguration properties = getInstance().getPropertiesConfiguration();
        return properties.getString(key);
    }
}
