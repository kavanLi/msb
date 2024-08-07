package com.example.demo.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.util.ListUtils;
import com.example.demo.service.easyExcel.EasyExcelService;
import com.mashibing.internal.common.constant.ServerRespCode;
import com.mashibing.internal.common.domain.request.ItemManagermentQueryReq;
import com.mashibing.internal.common.annotation.RateLimit;
import com.mashibing.internal.common.domain.response.ResponseResult;
import com.mashibing.internal.common.exception.SmartyunstException;
import com.mashibing.internal.common.config.easyExcel.utils.EasyExcelUtils;
import com.mashibing.internal.common.config.easyExcel.YchItemDownloadData;
import com.mashibing.internal.common.config.easyExcel.setting.SheetTableData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 易车汇-款项管理
 *
 * @author lijj
 */
@RestController
@Slf4j
public class EasyExcelController {

    @Resource
    private EasyExcelService easyExcelService;


    /**
     * 门店端-款项管理-导出 启动/未启用 发布/未发布
     * @return *
     */
    @PostMapping("/item/download/dataExport")
    @RateLimit(permitsPerSecond = 1, interval = 3) // 限流  每3秒限制1个请求
    public void itemDataExport(ItemManagermentQueryReq req, HttpServletResponse response){
        //List <YchItemDownloadData> respList = smartyunstItemManagementService.itemDataExport(req);
        List <YchItemDownloadData> respList = ListUtils.newArrayList();

        // 工具类导出数据
        try {
            List <SheetTableData> sheetTableDataList = new ArrayList <>();
            sheetTableDataList.add(new SheetTableData(YchItemDownloadData.class, respList));

            EasyExcelUtils.excel4MultiProtectedTablesDownload(response, EasyExcelUtils.ITEM_NAME, sheetTableDataList, EasyExcelUtils.DEFAULT_WATER_MARK_CONTENT, false);
        } catch (IOException e) {
            log.error("款项导出失败：{}", e.getLocalizedMessage());
            throw new SmartyunstException(ServerRespCode.FAIL, "导出失败！");
        }
    }

    /**
     * 门店端-款项管理-导出模板下载
     * @return *
     */
    @PostMapping("/item/download/importTemplate")
    @RateLimit(permitsPerSecond = 1, interval = 3) // 限流  每3秒限制1个请求
    public void downloadImportTemplate(ItemManagermentQueryReq req, HttpServletResponse response){
        List <YchItemDownloadData> respList = ListUtils.newArrayList();
        // 工具类导出数据
        try {
            List <SheetTableData> sheetTableDataList = new ArrayList <>();
            sheetTableDataList.add(new SheetTableData(YchItemDownloadData.class, respList));

            EasyExcelUtils.excel4MultiProtectedTablesDownload(response, EasyExcelUtils.ITEM_IMPORT_TEMPLATE, sheetTableDataList, EasyExcelUtils.DEFAULT_WATER_MARK_CONTENT, false);
        } catch (IOException e) {
            log.error("款项导出失败：{}", e.getLocalizedMessage());
            throw new SmartyunstException(ServerRespCode.FAIL, "导出失败！");
        }
    }

    /**
     * 门店端-款项管理-批量导入
     * @return *
     */
    @PostMapping("/item/upload/dataImport")
    @RateLimit(permitsPerSecond = 1, interval = 3) // 限流  每3秒限制1个请求
    public ResponseResult <?> itemDataImport(ItemManagermentQueryReq req, @RequestParam("file") MultipartFile file){
        try {
            //选定当前登录用户的平台
            //LoginEmployeeDTO loginEmployee = LoginEmployeeThreadLocal.get();
            return ResponseResult.success(easyExcelService.itemDataImport(req, file));
        } catch (Exception e) {
            log.error("款项批量导入失败：{}", e.getLocalizedMessage());
            throw new SmartyunstException(ServerRespCode.FAIL, "款项批量导入失败！：" + e.getCause().getLocalizedMessage());
        }
    }




}
