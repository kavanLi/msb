package com.example.demo.easyexcel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.mashibing.internal.common.config.easyExcel.YchItemDownloadData;
import com.mashibing.internal.common.config.easyExcel.read.ReadExcelResult;
import com.mashibing.internal.common.config.easyExcel.utils.ExcelCommonUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * listener不能放到common模块里面，因为要引入spring容易维护的DAO SERVICE
 */
@Slf4j
@NoArgsConstructor
public class YchItemReadDataListener extends AnalysisEventListener <YchItemDownloadData> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private final LocalDateTime now = LocalDateTime.now();
    private Boolean errorHeader = false;

    /**
     * 缓存的数据
     */
    //private List <SmartyunstItemManagement> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private List <String> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 返回的导入结果数据
     */
    private List <String> successResultMessages = ListUtils.newArrayList();
    private List <String> errorResultMessages = ListUtils.newArrayList();
    private int currentRowIndex = 0; // 维护当前行号
    private String applicationId;
    private String applicationName;
    private String bizUserId;
    private String memberName;
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    //private EasyExcelService smartyunstItemManagementService;
    //
    //private ISaasYchMemberService saasYchMemberService;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    //public YchItemReadDataListener(EasyExcelService smartyunstItemManagementService,
    //                               ISaasYchMemberService saasYchMemberService, String applicationId,
    //                               String applicationName, String bizUserId, String memberName) {
    //    this.applicationId = applicationId;
    //    this.applicationName = applicationName;
    //    this.bizUserId = bizUserId;
    //    this.memberName = memberName;
    //    this.smartyunstItemManagementService = smartyunstItemManagementService;
    //    this.saasYchMemberService = saasYchMemberService;
    //}

    @Override
    public void invokeHead(Map <Integer, ReadCellData <?>> headMap, AnalysisContext context) {
        // 通过反射获取 YchItemDownloadData 类的期望表头顺序
        List <String> expectedHeaders = ExcelCommonUtil.getExpectedHeaders(YchItemDownloadData.class);

        // 遍历实际表头，并与期望表头进行比较
        for (Map.Entry <Integer, ReadCellData <?>> entry : headMap.entrySet()) {
            Integer index = entry.getKey();
            String actualHeader = entry.getValue().getStringValue();

            if (index < expectedHeaders.size() && !expectedHeaders.get(index).equals(actualHeader)) {
                String formattedMessage = String.format("模板第%d列的表头不匹配！当前表头: %s，期望的表头: %s", index + 1, actualHeader, expectedHeaders.get(index));
                errorResultMessages.add(formattedMessage);
                log.error(formattedMessage);
            }
        }

        if (CollectionUtil.isNotEmpty(errorResultMessages)) {
            errorHeader = true;
        }
    }

    @Override
    public void invoke(YchItemDownloadData data, AnalysisContext context) {
        if (errorHeader) {
            return;
        }
        // 更新当前行号
        currentRowIndex = context.readRowHolder().getRowIndex();
        log.info("解析第{}行数据:{}", currentRowIndex, JSON.toJSONString(data));
        //SmartyunstItemManagement smartyunstItemManagement = new SmartyunstItemManagement();
        //BeanUtils.mergeObjects(data, smartyunstItemManagement);
        //
        //// 赋默认值
        //smartyunstItemManagement.setApplicationId(applicationId);
        //smartyunstItemManagement.setApplicationName(applicationName);
        //smartyunstItemManagement.setBizUserId(bizUserId);
        //smartyunstItemManagement.setMemberName(memberName);
        //smartyunstItemManagement.setStatus(ItemManagementConstants.ItemStatus.ITEM_STATUS_DISABLE.getCode());
        //smartyunstItemManagement.setItemTier(ItemManagementConstants.ItemTier.ITEM_DETAIL.getCode());
        //smartyunstItemManagement.setSeqNo(999);
        //smartyunstItemManagement.setGmtCreate(now);
        //smartyunstItemManagement.setGmtModified(now);

        // 判断收款方角色 判断收款方角色是否有效
        Boolean valid4ReceiverId = false;
        // 有效 -> 重新赋值
        //if (StringUtils.isNotEmpty(smartyunstItemManagement.getReceiverId())) {
        //    ServerResp <List <YchReceiverResp>> receiverList = saasYchMemberService.getReceiverList(applicationId, bizUserId);
        //    for (YchReceiverResp receiverResp : receiverList.getData()) {
        //        if (receiverResp.getBizUserId().equals(smartyunstItemManagement.getReceiverId())) {
        //            smartyunstItemManagement.setReceiverId(receiverResp.getBizUserId());
        //            smartyunstItemManagement.setReceiverName(receiverResp.getMemberName());
        //            valid4ReceiverId = true;
        //            break;
        //        }
        //    }
        //}
        // 无效
        if (!valid4ReceiverId) {
            // 获取字段的注解值
            setResponseMessage(data, data.getReceiverId(), "第%d行，%s列解析异常/为空/无分账权限，数据为:%s", "receiverId");
            return;
        }

        // 款项编号不能为空，并查重
        Boolean valid4ItemNo = false;
        //if (StringUtils.isNotEmpty(smartyunstItemManagement.getItemNo())) {
        //    // 为了保持款项大类小类的结构 款项编号需要进行去重判断
        //    SmartyunstItemManagement distinct = smartyunstItemManagementService.getOne(new LambdaQueryWrapper <SmartyunstItemManagement>()
        //            .eq(SmartyunstItemManagement::getApplicationId, smartyunstItemManagement.getApplicationId())
        //            .eq(SmartyunstItemManagement::getBizUserId, smartyunstItemManagement.getBizUserId())
        //            .eq(SmartyunstItemManagement::getItemNo, smartyunstItemManagement.getItemNo())
        //    );
        //    if (Objects.isNull(distinct)) {
        //        valid4ItemNo = true;
        //    }
        //}
        if (!valid4ItemNo) {
            setResponseMessage(data, data.getItemNo(), "第%d行，%s列解析异常/为空/存在重复款项明细编号的款项，数据为:%s", "itemNo");
            return;
        }

        // 款项类明细名称不为空
        //if (StringUtils.isEmpty(smartyunstItemManagement.getItemName())) {
        //    setResponseMessage(data, data.getItemName(), "第%d行，%s列解析异常/为空/存在重复款项明细编号的款项，数据为:%s", "itemName");
        //    return;
        //}
        //
        //// 款项类别名称不为空
        //if (StringUtils.isEmpty(data.getItemName())) {
        //    setResponseMessage(data, data.getParentItemName(), "第%d行，%s列解析异常/为空/存在重复款项明细编号的款项，数据为:%s", "parentItemName");
        //    return;
        //}
        // 父款项编号不能为空，添加限定条件
        Boolean valid4ParentItemNo = false;
        //if (StringUtils.isNotEmpty(smartyunstItemManagement.getParentItemNo())) {
        //    // 查询当前款项明细是否有父款项类别，若没有则新增
        //    SmartyunstItemManagement parent =
        //            smartyunstItemManagementService.getOne(new LambdaQueryWrapper <SmartyunstItemManagement>()
        //                    .eq(SmartyunstItemManagement::getApplicationId, smartyunstItemManagement.getApplicationId())
        //                    .eq(SmartyunstItemManagement::getBizUserId, smartyunstItemManagement.getBizUserId())
        //                    .eq(SmartyunstItemManagement::getItemNo, smartyunstItemManagement.getParentItemNo())
        //                    .eq(SmartyunstItemManagement::getParentItemNo, smartyunstItemManagement.getParentItemNo())
        //            );
        //    if (Objects.isNull(parent)) {
        //        SmartyunstItemManagement parentItemManagement = new SmartyunstItemManagement();
        //        // 赋默认值
        //        parentItemManagement.setApplicationId(applicationId);
        //        parentItemManagement.setApplicationName(applicationName);
        //        parentItemManagement.setBizUserId(bizUserId);
        //        parentItemManagement.setMemberName(memberName);
        //        parentItemManagement.setItemNo(smartyunstItemManagement.getParentItemNo());
        //        parentItemManagement.setItemName(data.getParentItemName());
        //        parentItemManagement.setParentItemNo(smartyunstItemManagement.getParentItemNo());
        //        parentItemManagement.setItemNo(smartyunstItemManagement.getParentItemNo());
        //        parentItemManagement.setItemTier(ItemManagementConstants.ItemTier.ITEM_CATEGORY.getCode());
        //        parentItemManagement.setStatus(ItemManagementConstants.ItemStatus.ITEM_STATUS_DISABLE.getCode());
        //        parentItemManagement.setItemScene(smartyunstItemManagement.getItemScene());
        //        parentItemManagement.setItemDesc(smartyunstItemManagement.getItemDesc());
        //        parentItemManagement.setIsRealName(smartyunstItemManagement.getIsRealName());
        //        smartyunstItemManagementService.save(parentItemManagement);
        //    }
        //    valid4ParentItemNo = true;
        //}
        if (!valid4ParentItemNo) {
            setResponseMessage(data, data.getParentItemNo(), "第%d行，%s列解析异常/为空/存在重复款项明细编号的款项，数据为:%s", "parentItemNo");
            return;
        }
        //cachedDataList.add(smartyunstItemManagement);

        String resultMessage = String.format("第%d行数据解析入库成功！", currentRowIndex);
        successResultMessages.add(resultMessage);

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        log.info("所有数据解析完成！");
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            String formattedMessage = String.format("第%d行，第%s列解析异常/为空，数据为:%s",
                    excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(),
                    excelDataConvertException.getCellData());
            errorResultMessages.add(formattedMessage);
            log.error(formattedMessage);
        }
    }

    /**
     * 设定每行解析返回信息
     */
    private void setResponseMessage(YchItemDownloadData ychItemDownloadData, String dataValue,
                                    String defaultMessage,
                                    String field) {
        // 获取字段的注解值
        String receiverIdHeader = ExcelCommonUtil.getExcelPropertyValue(ychItemDownloadData, field);
        String resultMessage = String.format(defaultMessage,
                currentRowIndex,
                receiverIdHeader,
                dataValue);
        errorResultMessages.add(resultMessage);
        log.info(resultMessage);
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("读到第{}条数据，开始存储数据库！", cachedDataList.size());
        //smartyunstItemManagementService.saveBatch(cachedDataList);
        log.info("存储{}条数据库成功！", cachedDataList.size());
    }

    public int getCurrentRowIndex() {
        return currentRowIndex;
    }

    public String getResultMessages() {
        String formattedMessage = String.format("解析入库成功%d行：\n%s\n------------\n解析失败%d行：\n%s\n",
                successResultMessages.size(),
                String.join("\n", successResultMessages),
                errorResultMessages.size(),
                String.join("\n", errorResultMessages));
        return formattedMessage;
    }

    public ReadExcelResult getReadExcelResult() {
        Integer successCount = successResultMessages.size();
        String successDetails = String.join("\n", successResultMessages);
        Integer errorCount = errorResultMessages.size();
        String errorDetails = String.join("\n", errorResultMessages);
        return new ReadExcelResult(successCount, successDetails, errorCount, errorDetails);
    }
}
