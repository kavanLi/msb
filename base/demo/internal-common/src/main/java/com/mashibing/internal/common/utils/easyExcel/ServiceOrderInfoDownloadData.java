package com.mashibing.internal.common.utils.easyExcel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.mashibing.internal.common.utils.easyExcel.converter.YchServiceOrderInfoEnumConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
@Accessors(chain=true)
public class ServiceOrderInfoDownloadData {

    /**
     * 忽略这个字段
     */
    /**主键*/
    @ExcelIgnore
    private Long id;

    /**
     * 业务类型
     */
    @ExcelProperty("业务类型")
    private String businessType;

    /**
     * 门店ID
     */
    @ExcelProperty("门店ID")
    private String storeId;

    /**
     * 门店名称
     */
    @ExcelProperty("门店名称")
    private String storeName;

    /**
     * 销售ID
     */
    @ExcelProperty("销售ID")
    private String salesId;

    /**
     * 销售名称
     */
    @ExcelProperty("销售名称")
    private String salesName;

    /**
     * 账单号
     */
    @ExcelProperty("账单号")
    private String serviceOrderNo;

    /**
     * 实名认证标识 0；否；1：是 保险场景必须上送“1”
     */
    @ExcelProperty(value = "实名认证标识", converter = YchServiceOrderInfoEnumConverter.class)
    private String isRealName;

    /**
     * 顾客类型 单位：COR；个人：IND，默认为个人
     */
    @ExcelProperty(value = "顾客类型", converter = YchServiceOrderInfoEnumConverter.class)
    private String customerType;

    /**
     * 顾客姓名
     */
    @ExcelProperty("顾客姓名")
    private String customerName;

    /**
     * 证件类型
     * 必须是指定的几种值 注: 身份证-1 " +
     *             "护照-2 " +
     *             "军官证-3 " +
     *             "回乡证-4 " +
     *             "台胞证-5 " +
     *             "警官证-6 " +
     *             "士兵证-7 " +
     *             "户口簿-8 " +
     *             "港澳居民来往内地通行证-9 " +
     *             "临时身份证-10 " +
     *             "外国人居留证-11 " +
     *             "港澳台居民居住证-12 " +
     *             "其它证件-99
     */
    @ExcelProperty(value = "证件类型", converter = YchServiceOrderInfoEnumConverter.class)
    private String certType;

    /**
     * 客户证件号
     */
    @ExcelProperty("客户证件号")
    private String customerCertNo;

    /**
     * 客户手机号
     */
    @ExcelProperty("客户手机号")
    private String customerPhone;

    /**
     * 账单金额
     */
    @ExcelProperty("账单金额（分）")
    private Long orderAmount;

    /**
     * 车牌号
     */
    @ExcelProperty("车牌号")
    private String carId;

    /**
     * 车架号
     */
    @ExcelProperty("车架号")
    private String vehicleId;

    /**
     * 订单时间
     */
    @ExcelProperty("订单时间")
    private String orderTime;

    /**
     * 支付类型
     */
    @ExcelProperty("支付类型")
    private String payType;

    /**
     * 收款标识 STANDARD：标准收款；ITEM：款项收款
     */
    @ExcelProperty(value = "收款标识", converter = YchServiceOrderInfoEnumConverter.class)
    private String collectionFlag;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;

    /**
     * 已支付金额
     */
    @ExcelProperty("已支付金额（分）")
    private Long paidAmount;

    /**
     * 状态 0未支付 1部分支付 2 完成
     */
    @ExcelProperty(value = "状态", converter = YchServiceOrderInfoEnumConverter.class)
    private String status;

    /**
     * 结算状态 0-未结算 1-结算中 2-结算成功 3-结算失败
     */
    @ExcelProperty(value = "结算状态", converter = YchServiceOrderInfoEnumConverter.class)
    private String settleStatus;

    /**
     * 结算批次号
     */
    @ExcelProperty("结算批次号")
    private String settleBatchNo;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private Date updateTime;




}
