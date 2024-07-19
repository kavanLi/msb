package com.mashibing.internal.common.utils.easyExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.mashibing.internal.common.utils.CustomDateTimeFormatter;
import com.mashibing.internal.common.utils.easyExcel.handler.MultiWaterMarkHandler;
import com.mashibing.internal.common.utils.easyExcel.handler.SingleWaterMarkHandler;
import com.mashibing.internal.common.utils.easyExcel.setting.SealBase64Data;
import com.mashibing.internal.common.utils.easyExcel.setting.SheetTableData;
import com.mashibing.internal.common.utils.easyExcel.setting.WaterMark;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-23 15:06
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public class EasyExcelUtils {

    public static String DEFAULT_WATER_MARK_CONTENT = "通联支付";
    public static String DAILY_FEE_FLOW_FILE_NAME = "出入金日结手续费账单-";
    public static String ITEM_NAME = "款项信息-";
    public static String MCHT_ORDER_AGENT_NAME = "代收交易查询-";
    public static String MCHT_ORDER_CONSUME_NAME = "消费交易查询-";
    public static String SERVICE_ORDER_INFO_NAME = "单据查询-";
    public static String BILL_FLOW_RECON_NAME = "账单流对账信息-";

    public static final DateTimeFormatter excelExportDateTimeFormatter = CustomDateTimeFormatter.YYYY_MM_DD_HH_MM_SS_DIFF_SPLIT.getInstance();


    /**
     * 下载多个带水印印章的表格（保护）
     *
     * @param response
     * @param fileName
     * @param sheetTableDataList 包含表格类和数据列表的集合
     * @param waterMarkContent 水印内容
     * @throws IOException
     */
    public static void excel4MultiProtectedTablesDownload(HttpServletResponse response, String fileName, List<SheetTableData> sheetTableDataList, String waterMarkContent) throws IOException {
        WaterMark waterMark = basicSetting4Response(response, fileName, waterMarkContent);

        try {
            // 使用ByteArrayOutputStream在内存中生成Excel文件
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // 使用 EasyExcel 创建 writer
            ExcelWriter writer = EasyExcel.write(outputStream)
                    // 设置水印要将 inMemory 设置为 true ,但是会有OOM的风险
                    .inMemory(true)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new SingleWaterMarkHandler(waterMark))
                    .build();

            // 创建一个 Sheet
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();

            // 循环写入表格数据
            for (int i = 0; i < sheetTableDataList.size(); i++) {
                SheetTableData tableData = sheetTableDataList.get(i);
                WriteTable writeTable = EasyExcel.writerTable(i).head(tableData.getClazz()).build();
                writer.write(tableData.getDataList(), writeSheet, writeTable);
            }

            // 关闭 writer
            writer.finish();

            // 将生成的Excel文件从ByteArrayOutputStream转换为ByteArrayInputStream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            // 使用Apache POI读取生成的Excel文件并设置工作表保护
            Workbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            sheet.protectSheet("password");

            // 插入图片印章
            // 将印章Base64字符串转换为InputStream
            InputStream stampImageInputStream = base64ToInputStream(SealBase64Data.SEAL_BASE64_STRING);
            insertSealImage(workbook, sheet, stampImageInputStream);

            // 将保护后的Excel文件写入到HTTP响应中
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while exporting Excel file", e);
        }
    }

    /**
     * 下载多个带水印印章表格（不受保护）
     *
     * @param response
     * @param fileName
     * @param sheetTableDataList 包含表格类和数据列表的集合
     * @param waterMarkContent 水印内容
     * @throws IOException
     */
    public static void excel4MultiTablesDownload(HttpServletResponse response, String fileName,
                                                          List<SheetTableData> sheetTableDataList, String waterMarkContent) throws IOException {
        WaterMark waterMark = basicSetting4Response(response, fileName, waterMarkContent);

        try {

            // 使用 EasyExcel 创建 writer
            ExcelWriter writer = EasyExcel.write(response.getOutputStream())
                    // 设置水印要将 inMemory 设置为 true ,但是会有OOM的风险
                    .inMemory(true)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new MultiWaterMarkHandler(waterMark))
                    .build();

            // 创建一个 Sheet
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();

            // 循环写入表格数据
            for (int i = 0; i < sheetTableDataList.size(); i++) {
                SheetTableData tableData = sheetTableDataList.get(i);
                WriteTable writeTable = EasyExcel.writerTable(i).head(tableData.getClazz()).build();
                writer.write(tableData.getDataList(), writeSheet, writeTable);
            }

            // 关闭 writer
            writer.finish();

        } catch (IOException e) {
            throw new RuntimeException("Error while exporting Excel file", e);
        }
    }

    /**
     * 下载一个表格
     */
    public static void excel4SingleTableDownload(HttpServletResponse response, String fileName,
                                      Class <?> clazz, List <?> dataList, String waterMarkContent) throws IOException {
        WaterMark waterMark = basicSetting4Response(response, fileName, waterMarkContent);

        write2ProtectedExcelWithWaterMark(response, clazz, dataList, waterMark);
    }

    /**
     * 下载一个带水印印章的表格（受保护）
     */
    private static void write2ProtectedExcelWithWaterMark(HttpServletResponse response, Class <?> clazz, List <?> dataList, WaterMark waterMark) throws IOException {
        // 使用ByteArrayOutputStream在内存中生成Excel文件
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        EasyExcel.write(outputStream, clazz)
                // 设置水印要将 inMemory 设置为 true ,但是会有OOM的风险
                .inMemory(true)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(new MultiWaterMarkHandler(waterMark))
                .sheet("Sheet1")
                .doWrite(dataList);

        // 将生成的Excel文件从ByteArrayOutputStream转换为ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // 使用Apache POI读取生成的Excel文件并设置工作表保护
        Workbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
        sheet.protectSheet("password");

        // 插入图片印章
        // 将印章Base64字符串转换为InputStream
        InputStream stampImageInputStream = base64ToInputStream(SealBase64Data.SEAL_BASE64_STRING);
        insertSealImage(workbook, sheet, stampImageInputStream);

        // 将保护后的Excel文件写入到HTTP响应中
        workbook.write(response.getOutputStream());
        workbook.close();
    }


    /**
     * 下载一个带水印印章的表格（不受保护）
     */
    private static void write2ExcelWithWaterMark(HttpServletResponse response, Class <?> clazz, List <?> dataList, WaterMark waterMark) throws IOException {
        EasyExcel.write(response.getOutputStream(), clazz)
                // 设置水印要将 inMemory 设置为 true ,但是会有OOM的风险
                .inMemory(true)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(new MultiWaterMarkHandler(waterMark))
                .sheet("Sheet1").doWrite(dataList);
    }

    /**
     * 最简单的表格下载
     */
    private static void write2Excel(HttpServletResponse response, Class <?> clazz, List <?> dataList) throws IOException {
        /**
         * 自动列宽(不太精确)
         *
         * 这个目前不是很好用，比如有数字就会导致换行。而且长度也不是刚好和实际长度一致。 所以需要精确到刚好列宽的慎用。 当然也可以自己参照
         */
        EasyExcel.write(response.getOutputStream(), clazz)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("Sheet1").doWrite(dataList);
    }


    /**
     * 基础文件流设置
     */
    private static WaterMark basicSetting4Response(HttpServletResponse response, String fileName, String waterMarkContent) throws UnsupportedEncodingException {
        // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet 支持压缩，通常用于新版本的 Microsoft Excel 文件 (.xlsx)
        // 。不兼容旧版本的 Microsoft Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        String now = excelExportDateTimeFormatter.format(LocalDateTime.now());
        //response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + dateTime + ".xlsx");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + now + ".xlsx");

        // 创建并注册自定义的水印处理器 需要设定 inMemory(true)
        if (StringUtils.isEmpty(waterMarkContent)) {
            waterMarkContent = "通联支付";
        }
        WaterMark watermark = new WaterMark();
        watermark.setContent(waterMarkContent)
                .setWidth(600)
                .setHeight(400)
                .setYAxis(200);

        return watermark;
    }

    /**
     * 图片base64转换成输入流
     */
    public static InputStream base64ToInputStream(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return new ByteArrayInputStream(decodedBytes);
    }

    /**
     * 插入印章
     */
    private static void insertSealImage(Workbook workbook, Sheet sheet, InputStream imageInputStream) throws IOException {
        // 读取图片
        byte[] bytes = IOUtils.toByteArray(imageInputStream);
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        imageInputStream.close();

        CreationHelper helper = workbook.getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        // 设置图片位置
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(5); // Column where the picture starts
        anchor.setRow1(20); // Row where the picture starts

        // 插入图片
        Picture picture = drawing.createPicture(anchor, pictureIdx);
        picture.resize(); // Adjust the image size
    }

}
