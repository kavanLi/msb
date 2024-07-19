package com.mashibing.internal.common.utils.easyExcel;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.mashibing.internal.common.utils.easyExcel.converter.YchItemBooleanConverter;
import com.mashibing.internal.common.utils.easyExcel.converter.YchItemEnumConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
@Accessors(chain=true)
public class YchItemDownloadData {

    /**
     * 忽略这个字段
     */
    /**主键*/
    @ExcelIgnore
    private Long id;

    /**
     * 商户用户编号 门店编号
     */
    @ExcelProperty("门店编号")
    private String bizUserId;

    /**
     * 门店名称
     */
    @ExcelProperty("门店名称")
    private String memberName;

    /**
     * 科目编号
     */
    @ExcelProperty("科目编号")
    private String itemNo;

    /**
     * 父科目编号
     */
    @ExcelProperty("父科目编号")
    private String parentItemNo;

    /**
     * 科目名称
     */
    @ExcelProperty("科目名称")
    private String itemName;

    /**
     * 科目描述
     */
    @ExcelProperty("科目描述")
    private String itemDesc;

    /**
     * 科目层级，1-科目类别 2-科目明细
     */
    @ExcelProperty(value = "科目层级", converter = YchItemEnumConverter.class)
    private Integer itemTier;

    /**
     * 应用场景，1-POS款项 2-H5款项
     */
    @ExcelProperty(value = "应用场景", converter = YchItemEnumConverter.class)
    private Integer itemScene;

    /**
     * 顺序号
     */
    @ExcelProperty("顺序号")
    private Integer seqNo;

    /**
     * 价格，单位：分
     */
    @ExcelProperty("价格（分）")
    private Long itemPrice;

    /**
     * 实名标识
     */
    @ExcelProperty(value = "是否实名", converter = YchItemBooleanConverter.class)
    private Boolean isRealName;

    /**
     * 是否已发布
     */
    @ExcelProperty(value = "是否发布", converter = YchItemBooleanConverter.class)
    private Boolean isReleased;

    /**
     * 是否可退款
     */
    @ExcelProperty(value = "是否可退款", converter = YchItemBooleanConverter.class)
    private Boolean isRefund;

    /**
     * 退款多少天之内可退款
     */
    @ExcelProperty("可退款期限（天）")
    private Integer refundDaysLimit;

    /**
     * 收款方编号
     */
    @ExcelProperty("收款方编号")
    private String receiverId;

    /**
     * 收款方名称
     */
    @ExcelProperty("收款方名称")
    private String receiverName;

    /**
     * 启用状态，1-启用 0-禁用
     */
    @ExcelProperty(value = "启用状态", converter = YchItemEnumConverter.class)
    private Integer status;

    /**
     * 登记标识 0-未登记 1-登记
     */
    @ExcelProperty(value = "登记标识", converter = YchItemBooleanConverter.class)
    private Boolean registryFlag;

    /**
     * 创建时间，格式：YYYY-MM-DD hh:mm:ss
     */
    @ExcelProperty("创建时间")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间，格式：YYYY-MM-DD hh:mm:ss
     */
    @ExcelProperty("更新时间")
    private LocalDateTime gmtModified;

}
