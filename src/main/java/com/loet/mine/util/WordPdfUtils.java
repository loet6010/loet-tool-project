package com.loet.mine.util;

import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordPdfUtils {
    private static Logger logger = LoggerFactory.getLogger(WordPdfUtils.class);

    public static void main(String[] args) throws Exception {
        String filepath = "E:\\财鱼记录\\test\\电签合同模板参考-20190416.docx";
        String outPath = "E:\\财鱼记录\\test\\test_new.pdf";

        InputStream source = new FileInputStream(filepath);
        OutputStream target = new FileOutputStream(outPath);

        Map<String, String> params = getReplaceMap();

        long timeStart = System.currentTimeMillis();
        wordConverterToPdf(source, target, params);
        long timeEnd = System.currentTimeMillis();

        logger.info("转换耗时：{}", timeEnd - timeStart);
    }


    /**
     * 将word文档， 转换成pdf, 中间替换掉变量
     *
     * @param source  源为word文档， 必须为docx文档
     * @param target  目标输出
     * @param params  需要替换的变量
     * @param options PdfOptions.create().fontEncoding( "windows-1250" ) 或者其他
     * @throws Exception
     */
    private static void wordConverterToPdf(InputStream source, OutputStream target, Map<String, String> params)
            throws Exception {
        XWPFDocument doc = new XWPFDocument(source);
        paragraphReplace(doc.getParagraphs(), params);
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    paragraphReplace(cell.getParagraphs(), params);
                }
            }
        }
        PdfConverter.getInstance().convert(doc, target, getPdfOptions());
    }

    /**
     * 替换段落中内容
     */
    private static void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            for (XWPFParagraph p : paragraphs) {
                for (XWPFRun r : p.getRuns()) {
                    String content = r.getText(r.getTextPosition());
                    logger.info(content);
                    if (StringUtils.isNotEmpty(content) && params.containsKey(content)) {
                        r.setText(params.get(content), 0);
                    }
                }
            }
        }
    }

    private static PdfOptions getPdfOptions() {
        PdfOptions options = PdfOptions.create();

        // 支持中文字体
        options.fontProvider(new ITextFontRegistry() {
            public Font getFont(String familyName, String encoding, float size, int style, Color color) {
                try {
                    BaseFont bfChinese = BaseFont.createFont("E:\\财鱼记录\\temp\\SimSun.ttf", BaseFont.IDENTITY_H,
                            BaseFont.EMBEDDED);
                    Font fontChinese = new Font(bfChinese, size, style, color);
                    if (familyName != null) {
                        fontChinese.setFamily(familyName);
                    }
                    return fontChinese;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
                }
            }


        });

        return options;
    }

    /**
     * 获取合同替换内容
     *
     * @param employeeInfo
     * @param employeeContract
     * @return
     */
    private static Map<String, String> getReplaceMap() {
        Map<String, String> replaceMap = new HashMap<>();

        // 姓名
        replaceMap.put("name", "张三");
        // 性别
        replaceMap.put("sex", "男");
        // 名族
        replaceMap.put("nation", "汉族");
        // 身份证号
        replaceMap.put("idCard", "422826199012095621");
        // 手机号码
        replaceMap.put("phone", "13566855264");

        Calendar startDate = Calendar.getInstance();
        // 合同开始_年
        replaceMap.put("startYear", String.valueOf(startDate.get(Calendar.YEAR)));
        // 合同开始_月
        replaceMap.put("startMonth", String.valueOf(startDate.get(Calendar.MONTH) + 1));
        // 合同开始_日
        replaceMap.put("startDay", String.valueOf(startDate.get(Calendar.DATE)));

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);
        // 合同结束_年
        replaceMap.put("endYear", String.valueOf(endDate.get(Calendar.YEAR)));
        // 合同结束_月
        replaceMap.put("endMonth", String.valueOf(endDate.get(Calendar.MONTH) + 1));
        // 合同结束_日
        replaceMap.put("endDay", String.valueOf(endDate.get(Calendar.DATE)));

        return replaceMap;
    }

}