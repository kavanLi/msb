package com.example.demo.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模糊查询
 *
 * @author: kavanLi
 * @create: 2020-01-06 14:07
 */
@RestController
@Slf4j
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    private JavaMailSender mailSender;


    @SneakyThrows
    @PostMapping(value = "/test")
    public void test2(String querystr, HttpServletResponse response) throws FileNotFoundException {
        try {
            int width = 400;
            int height = 800;

            // 设置字体和颜色
            File fontFile = new File("C://NotoSansCJKsc-Regular.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(fontFile));
            Font tinyFont = font.deriveFont(Font.PLAIN, 14);
            Font textFont = font.deriveFont(Font.PLAIN, 18);
            Font largeBoldFont = font.deriveFont(Font.BOLD, 24); // 创建大号加粗字体

            int startX = 20;
            int startY = 20;

            // 计算文本内容所需的高度
            startY += 25; // 图标占据的空间
            startY += 30; // 标题
            startY += 30; // 子标题
            startY += 30; // 标题间距
            startY += 30; // 第一行
            startY += 30; // 第二行
            startY += 15; // 间距
            startY += 30; // 第三行
            startY += 30; // 大类1
            startY += 30; // 款项1
            startY += 30; // 款项2
            startY += 30; // 款项3
            startY += 30; // 大类2
            startY += 30; // 款项4
            startY += 30; // 款项5
            startY += 30; // 款项6
            startY += 30; // 合计
            startY += 15; // 间距
            startY += 30; // 平台单号
            startY += 30; // 订单编号
            startY += 30; // 交易类别
            startY += 30; // 交易账号
            startY += 30; // 备注
            startY += 30; // 时间
            startY += 15; // 间距
            startY += 20; // 备注
            startY += 100; // xxx
            height = startY;

            // 创建具有计算高度的 BufferedImage
            BufferedImage bufferedImage = new BufferedImage(width, startY + 20, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();

            // 设置背景颜色
            g2d.setColor(new Color(249, 249, 249));
            g2d.fillRect(0, 0, width, startY + 20);

            g2d.setFont(textFont);
            g2d.setColor(Color.BLACK);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int headX = 20;
            int headY = 20;

            // 将 ICO 图片绘制在左上角
            // 绘制 ICO 图片
            File iconFile = new File("C://allinpay-logo.png");
            BufferedImage iconImage = ImageIO.read(iconFile);
            g2d.drawImage(iconImage, headX, headY, null); // 将 ICO 图片绘制在左上角
            //headX += 70;
            //headY += 25;

            // 绘制大号加粗字体
            g2d.setFont(largeBoldFont);
            //g2d.drawString("通联支付", headX, headY);
            //headX += 5;
            //headY += 30;
            //g2d.setFont(textFont);
            //g2d.drawString("ALLINPAY", headX, headY);

            //headY += 20;
            g2d.setFont(largeBoldFont);
            int amountX = width - g2d.getFontMetrics().stringWidth("签购单") - 20;
            g2d.drawString("签购单", amountX, headY + 35);
            g2d.setFont(textFont); // 恢复普通字体大小

            // 绘制文本内容
            startY = 110;
            g2d.drawString("============= 款项汇总单 =============", startX, startY);
            startY += 30;
            g2d.drawString("商户名称:上海浦东迅捷汽修有限公司", startX, startY);
            startY += 30;
            g2d.drawString("商户编号:66029005511186Z", startX, startY);
            startY += 30;
            g2d.drawString("终端号:133589222", startX, startY);
            startY += 15;
            // 设置浅色，例如灰色
            g2d.setColor(new Color(200, 200, 200));
            // 绘制一条从 (startX, startY) 到 (startX + textWidth, startY) 的直线
            g2d.drawLine(startX, startY, startX + 360, startY);
            g2d.setColor(Color.BLACK);
            startY += 30;
            g2d.drawString("大类1", startX, startY);
            startY += 30;
            // 20 是右边距
            amountX = width - g2d.getFontMetrics().stringWidth("100.00元") - 20;
            g2d.drawString("款项1", startX + 10, startY);
            g2d.drawString("100.00元", amountX, startY);
            startY += 30;
            amountX = width - g2d.getFontMetrics().stringWidth("200.00元") - 20;
            g2d.drawString("款项2", startX + 10, startY);
            g2d.drawString("200.00元", amountX, startY);
            startY += 30;
            amountX = width - g2d.getFontMetrics().stringWidth("300.00元") - 20;
            g2d.drawString("款项3", startX + 10, startY);
            g2d.drawString("300.00元", amountX, startY);
            startY += 30;
            g2d.drawString("大类2", startX, startY);
            startY += 30;
            // 20 是右边距
            amountX = width - g2d.getFontMetrics().stringWidth("100.00元") - 20;
            g2d.drawString("款项4", startX + 10, startY);
            g2d.drawString("100.00元", amountX, startY);
            startY += 30;
            amountX = width - g2d.getFontMetrics().stringWidth("200.00元") - 20;
            g2d.drawString("款项5", startX + 10, startY);
            g2d.drawString("200.00元", amountX, startY);
            startY += 30;
            amountX = width - g2d.getFontMetrics().stringWidth("300.00元") - 20;
            g2d.drawString("款项6", startX + 10, startY);
            g2d.drawString("300.00元", amountX, startY);
            startY += 30;
            amountX = width - g2d.getFontMetrics().stringWidth("600.00元") - 20;
            g2d.drawString("合计", startX, startY);
            g2d.drawString("1200.00元", amountX, startY);
            startY += 15;
            g2d.setColor(new Color(200, 200, 200));
            g2d.drawLine(startX, startY, startX + 360, startY);
            g2d.setColor(Color.BLACK);
            startY += 30;
            g2d.drawString("平台单号:D20230821104501685100256314", startX, startY);
            startY += 30;
            g2d.drawString("订单编号:1693454129516519424", startX, startY);
            startY += 30;
            g2d.drawString("交易类别:维修", startX, startY);
            startY += 30;
            g2d.drawString("交易账号:superAdmin", startX, startY);
            startY += 30;
            g2d.drawString("备注:尽快 急需", startX, startY);
            startY += 30;
            // 获取当前日期时间
            LocalDateTime now = LocalDateTime.now();
            // 定义格式化模式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
            // 格式化当前日期时间
            String formattedDateTime = now.format(formatter);
            g2d.drawString("时间:" + formattedDateTime, startX, startY);
            startY += 15;
            g2d.setColor(new Color(200, 200, 200));
            g2d.drawLine(startX, startY, startX + 360, startY);
            g2d.setColor(Color.BLACK);
            startY += 20;
            g2d.setFont(tinyFont);
            g2d.drawString("此电子回单仅供参考，请妥善保管", startX + 70, startY);

            startY += 30;
            startY += 30;
            g2d.drawString("融易车", startX + 10, startY);
            startY += 20;
            g2d.drawString("一个美好生活的小程序", startX + 10, startY);
            // 二维码
            File qrImageFile = new File("C://qrcode.jpg");
            BufferedImage qrImage = ImageIO.read(qrImageFile);
            int qrImageWidth = qrImage.getWidth();
            int qrImageHeight = qrImage.getHeight();
            int qrX = width - qrImageWidth - 20; // 20 是右边距
            int qrY = height - qrImageHeight - 20; // 20 是下边距
            g2d.drawImage(qrImage, qrX, qrY, null);

            // 释放资源
            g2d.dispose();

            // 设置响应头
            response.setContentType("image/png");
            response.setCharacterEncoding("utf-8");
            String fileName = "receipt.png";
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // 将图像输出到响应流
            ImageIO.write(bufferedImage, "png", response.getOutputStream());
            response.getOutputStream().flush();

            System.out.println("电子回单生成成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //    try (PDDocument document = new PDDocument()) {
        //        PDPage page = new PDPage();
        //        document.addPage(page);
        //
        //        File fontFile = new File("C://NotoSansCJKsc-Regular.ttf");
        //        InputStream fontStream = new FileInputStream(fontFile);
        //        PDType0Font font = PDType0Font.load(document, fontStream);
        //
        //        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
        //            contentStream.beginText();
        //            contentStream.setFont(font, 12);
        //            contentStream.setLeading(14.5f);
        //            contentStream.newLineAtOffset(25, 700);
        //
        //            contentStream.showText("转出成功:");
        //            contentStream.newLine();
        //            contentStream.showText("收款方户名: 张三");
        //            contentStream.newLine();
        //            contentStream.showText("收款方账号: 6222 **** **** ***2 872");
        //            contentStream.newLine();
        //            contentStream.showText("收款方银行: 中国工商银行");
        //            contentStream.newLine();
        //            contentStream.showText("转账金额: 100.00元");
        //            contentStream.newLine();
        //            contentStream.showText("付款方户名: 李四");
        //            contentStream.newLine();
        //            contentStream.showText("付款方账号: 6214 **** **** 1963");
        //            contentStream.newLine();
        //            contentStream.showText("转账附言: 转账");
        //            contentStream.newLine();
        //            contentStream.showText("转账流水号: X6WTAQT005EI7IM3");
        //            contentStream.newLine();
        //            contentStream.showText("交易时间: 2024年07月10日");
        //
        //            contentStream.endText();
        //        }
        //
        //        // 设置响应头
        //        response.setContentType("application/pdf");
        //        response.setCharacterEncoding("utf-8");
        //        String fileName = "receipt.pdf";
        //        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        //        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        //
        //        // 将 PDF 输出到响应流
        //        document.save(response.getOutputStream());
        //        response.getOutputStream().flush();
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //}
    }
}
