package com.loet.mine.converter;

import java.io.*;

/**
 * @Author liurh
 * @Description DocxTest
 * @Date 2019/4/28
 */
public class DocxTest {
    public static void main(String[] args) throws Exception {
        String path = "E:\\财鱼记录\\temp\\test.pdf";
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        String inPath = "E:\\财鱼记录\\temp\\电签合同模板参考-20190416.docx";
        File inFile = new File(inPath);
        InputStream inputStream = new FileInputStream(inFile);


        Converter converter = new DocxToPDFConverter(inputStream, outputStream, true, true);
        converter.convert();
    }
}
