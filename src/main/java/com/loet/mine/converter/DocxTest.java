package com.loet.mine.converter;

import com.loet.mine.util.WordPdfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Author liurh
 * @Description DocxTest
 * @Date 2019/4/28
 */
public class DocxTest {
    private static Logger logger = LoggerFactory.getLogger(DocxTest.class);

    public static void main(String[] args) throws Exception {
        String path = "E:\\财鱼记录\\temp\\test.pdf";
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        String inPath = "E:\\财鱼记录\\temp\\电签合同模板参考-20190416.docx";
        File inFile = new File(inPath);
        InputStream inputStream = new FileInputStream(inFile);

        long timeStart = System.currentTimeMillis();
        Converter converter = new DocxToPDFConverter(inputStream, outputStream, true, true);
        converter.convert();
        long timeEnd = System.currentTimeMillis();

        logger.info("转换耗时：{}" , timeEnd - timeStart);
    }
}
