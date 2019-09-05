package com.loet.mine.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * PDF工具类
 */
public class PDFUtils {

    /**
     * PDF文件转为图片
     *
     * @param pdfFilePath
     * @return
     * @throws Exception
     */
    public static byte[] pdf2multiImage(String pdfFilePath) throws Exception {
        InputStream in = new FileInputStream(pdfFilePath);
        return pdf2multiImage(in);
    }

    /**
     * PDF输入流转为图片
     *
     * @param inputStream
     * @return
     */
    public static byte[] pdf2multiImage(InputStream inputStream) throws Exception {
        byte[] result;
        PDDocument pdf = PDDocument.load(inputStream);
        int actSize = pdf.getNumberOfPages();
        List<BufferedImage> picList = new ArrayList<>();
        for (int i = 0; i < actSize; i++) {
            BufferedImage image = new PDFRenderer(pdf).renderImageWithDPI(i, 130, ImageType.RGB);
            picList.add(image);
        }
        result = yPic(picList);
        inputStream.close();

        return result;
    }

    /**
     * 将宽度相同的图片，竖向追加在一起
     * 注意：宽度必须相同
     *
     * @param picList 文件流数组
     */
    private static byte[] yPic(List<BufferedImage> picList) throws Exception {
        if (picList == null || picList.size() <= 0) {
            return null;
        }

        int height = 0, // 总高度
                width = 0, // 总宽度
                _height, // 临时的高度 , 或保存偏移高度
                __height, // 临时的高度，主要保存每个高度
                picNum = picList.size();// 图片的数量
        int[] heightArray = new int[picNum]; // 保存每个文件的高度
        BufferedImage buffer; // 保存图片流
        List<int[]> imgRGB = new ArrayList<>(); // 保存所有的图片的RGB
        int[] _imgRGB; // 保存一张图片中的RGB数据
        for (int i = 0; i < picNum; i++) {
            buffer = picList.get(i);
            heightArray[i] = _height = buffer.getHeight();// 图片高度
            if (i == 0) {
                width = buffer.getWidth();// 图片宽度
            }
            height += _height; // 获取总高度
            _imgRGB = new int[width * _height];// 从图片中读取RGB
            _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
            imgRGB.add(_imgRGB);
        }
        _height = 0; // 设置偏移高度为0
        // 生成新图片
        BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < picNum; i++) {
            __height = heightArray[i];
            if (i != 0) {
                _height += __height; // 计算偏移高度
            }
            imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(imageResult, "jpg", out);

        return out.toByteArray();
    }

    public static byte[] bufferedImage2ByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\liurh\\Desktop\\自由职业者服务协议-杭州搜听.pdf";
        try {
            pdf2multiImage(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
