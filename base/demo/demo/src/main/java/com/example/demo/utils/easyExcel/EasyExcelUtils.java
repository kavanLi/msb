package com.example.demo.utils.easyExcel;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.example.demo.utils.DateOrTimeUtils;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-23 15:06
 * To change this template use File | Settings | File and Code Templates.
 */
public class EasyExcelUtils {

    public static String DAILY_FEE_FLOW_FILE_NAME = "出入金日结手续费账单-";

    /**
     * 导出excel
     *
     * @param response
     * @param fileName
     * @param clazz
     * @param dataList
     * @throws IOException
     */
    public static void excel4DailyFeeFlowDownload(HttpServletResponse response, String fileName, Class <YchDailyFee4DownloadData> clazz, List <YchDailyFee4DownloadData> dataList) throws IOException {
        // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet 支持压缩，通常用于新版本的 Microsoft Excel 文件 (.xlsx)
        // 。不兼容旧版本的 Microsoft Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        String dateTime = DateOrTimeUtils.dateTimeFormatter.format(LocalDateTime.now());
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + dateTime + ".xlsx");

        /**
         * 自动列宽(不太精确)
         *
         * 这个目前不是很好用，比如有数字就会导致换行。而且长度也不是刚好和实际长度一致。 所以需要精确到刚好列宽的慎用。 当然也可以自己参照
         */
        EasyExcel.write(response.getOutputStream(), clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("Sheet1").doWrite(dataList);
    }


}
