package com.loet.mine.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis Generator
 */
public class GeneratorUtils {
    public static void main(String[] args){
        System.out.println("执行 Generator 开始");
        try {
            String filePath = Thread.currentThread().getContextClassLoader().getResource("generatorConfig.xml").toURI()
                    .getPath();
            executeGenerator(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("执行 Generator 结束");
    }

    /**
     * 执行Generator生成文件
     *
     * @param filePath
     */
    private static void executeGenerator(String filePath) throws Exception{
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new File(filePath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

}
