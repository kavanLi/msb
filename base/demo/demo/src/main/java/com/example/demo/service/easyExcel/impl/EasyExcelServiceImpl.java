package com.example.demo.service.easyExcel.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.example.demo.easyexcel.YchItemReadDataListener;
import com.example.demo.service.easyExcel.EasyExcelService;
import com.mashibing.internal.common.constant.ServerRespCode;
import com.mashibing.internal.common.domain.request.ItemManagermentQueryReq;
import com.mashibing.internal.common.exception.SmartyunstException;
import com.mashibing.internal.common.config.easyExcel.YchItemDownloadData;
import com.mashibing.internal.common.config.easyExcel.read.ReadExcelResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kavanLi-R7000
 * @description 针对表【smartyunst_item_management】的数据库操作Service实现
 * @createDate 2024-02-22 16:18:54
 */
@Service
@Slf4j
public class EasyExcelServiceImpl implements EasyExcelService {

    @Override
    public List <YchItemDownloadData> itemDataExport(ItemManagermentQueryReq req) {
        //3baseService.hasApiAuth(req);

        //LambdaQueryWrapper <SmartyunstItemManagement> queryWrapper = new LambdaQueryWrapper <>();
        //
        ////根据当前集团编号查询对应商品订单
        //getQueryAppList(req.getApplicationId(), queryWrapper, req.getDepartmentId(), req.getBranchId());
        ////else {
        ////    throw new SmartyunstException(ServerRespCode.ERROR_PARAM, "账号集团编号不能为空！");
        ////}
        ////商户用户编号不为空，添加限定条件
        //if (StringUtils.isNotEmpty(req.getBizUserId())) {
        //    queryWrapper.eq(SmartyunstItemManagement::getBizUserId, req.getBizUserId());
        //}
        //
        ////按创建时间进行排序
        //queryWrapper.orderByDesc(SmartyunstItemManagement::getGmtCreate);
        //List <SmartyunstItemManagement> smartyunstItemManagements = this.list(queryWrapper);
        //List <YchItemDownloadData> respList = new ArrayList <>();
        //
        ////按创建时间进行排序
        //if (!CollectionUtils.isEmpty(smartyunstItemManagements)) {
        //    for (SmartyunstItemManagement item : smartyunstItemManagements) {
        //        YchItemDownloadData resp = new YchItemDownloadData();
        //        BeanUtils.mergeObjects(item, resp);
        //        respList.add(resp);
        //    }
        //}
        //
        //// ------------- 把款项类别名称信息提取出来合并到明细信息 -------------
        //Map <String, YchItemDownloadData> tier1Map = new HashMap <>();
        List <YchItemDownloadData> tier2List = new ArrayList <>();

        //for (YchItemDownloadData item : respList) {
        //    if (item.getItemTier() == ItemManagementConstants.ItemTier.ITEM_CATEGORY.getCode()) {
        //        tier1Map.put(item.getItemNo(), item);
        //    } else if (item.getItemTier() == ItemManagementConstants.ItemTier.ITEM_DETAIL.getCode()) {
        //        tier2List.add(item);
        //    }
        //}
        //
        //for (YchItemDownloadData ychItemDownloadData : tier2List) {
        //    ychItemDownloadData.setParentItemName(tier1Map.get(ychItemDownloadData.getParentItemNo()).getItemName());
        //}

        return tier2List;
    }

    @Override
    public ReadExcelResult itemDataImport(ItemManagermentQueryReq req, MultipartFile file) throws IOException {
        // Check if the file is empty
        if (file.isEmpty()) {
            throw new SmartyunstException(ServerRespCode.ERROR_PARAM, "上传的文件为空！");
        }

        // Check the MIME type
        String mimeType = file.getContentType();
        if (!(mimeType.equals("application/vnd.ms-excel") ||
                mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
            throw new SmartyunstException(ServerRespCode.ERROR_PARAM, "上传的文件必须是excel文件！");
        }

        // Check the file extension
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!(fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx"))) {
            throw new SmartyunstException(ServerRespCode.ERROR_PARAM, "上传的文件必须是.xls或.xlsx扩展名的excel文件！");
        }

        //SmartyunstMember smartyunstMember = baseService.hasApiAuth(req);
        //
        //SmartyunstAppInfo appInfo;
        //if (StringUtils.isNotEmpty(req.getApplicationId())) {
        //    appInfo = smartyunstAppInfoService.getAppInfo(req.getApplicationId());
        //} else {
        //    throw new SmartyunstException(ServerRespCode.ERROR_PARAM, "账号集团编号不能为空！");
        //}
        //
        ////商户用户编号不能为空，添加限定条件
        //if (StringUtils.isNotEmpty(req.getBizUserId())) {
        //} else {
        //    throw new SmartyunstException(ServerRespCode.ERROR_PARAM, "商户用户编号不能为空！");
        //}

        //YchItemReadDataListener listener = new YchItemReadDataListener(this, saasYchMemberService,
        //        req.getApplicationId(), appInfo.getApplicationName(),
        //        req.getBizUserId(), smartyunstMember.getMemberName());
        YchItemReadDataListener listener = new YchItemReadDataListener();
        EasyExcel.read(file.getInputStream(), YchItemDownloadData.class, listener)
                .sheet()
                .doRead();
        return listener.getReadExcelResult();
    }
}




