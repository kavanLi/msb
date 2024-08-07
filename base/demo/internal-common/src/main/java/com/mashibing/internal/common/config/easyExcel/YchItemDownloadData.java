package com.mashibing.internal.common.config.easyExcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.mashibing.internal.common.constant.ItemManagementConstants;
import com.mashibing.internal.common.config.easyExcel.annotation.DropdownOptions;
import com.mashibing.internal.common.config.easyExcel.converter.LongFormatConverter;
import com.mashibing.internal.common.config.easyExcel.converter.YchItemBooleanConverter;
import com.mashibing.internal.common.config.easyExcel.converter.YchItemEnumConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Accessors(chain = true) 读excel不能使用这个注解，否则会解析失败
 * https://blog.51cto.com/YangPC/5483482
 */

@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
public class YchItemDownloadData {

    /**
     * 忽略这个字段
     */
    /**
     * 主键
     */
    @ExcelIgnore
    private Long id;

    /**
     * 科目层级，1-科目类别 2-科目明细
     */
    @ExcelIgnore
    private Integer itemTier;

    //@ExcelProperty(value = "启用状态", order = 0, converter = YchItemEnumConverter.class)
    //@DropdownOptions(enumClass = ItemManagementConstants.ItemStatus.class, value = {"启用", "禁用"}, check = false)
    @ExcelIgnore
    private Integer status;

    @ExcelProperty(value = "款项场景", order = 0, converter = YchItemEnumConverter.class)
    @DropdownOptions(enumClass = ItemManagementConstants.ItemScene.class, value = {"POS款项", "H5款项"})
    private Integer itemScene;

    @ExcelProperty(value = "款项类别编号", order = 1)
    private String parentItemNo;

    @ExcelProperty(value = "款项类别名称", order = 2)
    private String parentItemName;

    @ExcelProperty(value = "款项明细编号", order = 3)
    private String itemNo;

    @ExcelProperty(value = "款项明细名称", order = 4)
    private String itemName;

    @ExcelProperty(value = "款项价格（元）", order = 5, converter = LongFormatConverter.class)
    private Long itemPrice;

    //@ExcelProperty("收款方编号")
    //private String bizUserId;
    //
    //@ExcelProperty("收款方名称")
    //private String memberName;

    @ExcelProperty(value = "是否实名", order = 6, converter = YchItemBooleanConverter.class)
    @DropdownOptions(enumClass = ItemManagementConstants.BooleanStatus.class, value = {"是", "否"})
    private Boolean isRealName;

    @ExcelProperty(value = "是否可退款", order = 7, converter = YchItemBooleanConverter.class)
    @DropdownOptions(enumClass = ItemManagementConstants.BooleanStatus.class, value = {"是", "否"})
    private Boolean isRefund;

    @ExcelProperty(value = "可退款期限（天）", order = 8)
    private Integer refundDaysLimit;

    @ExcelProperty(value = "是否登记", order = 9, converter = YchItemBooleanConverter.class)
    @DropdownOptions(enumClass = ItemManagementConstants.BooleanStatus.class, value = {"是", "否"})
    private Boolean registryFlag;

    @ExcelProperty(value = "金额是否可修改", order = 10, converter = YchItemBooleanConverter.class)
    @DropdownOptions(enumClass = ItemManagementConstants.BooleanStatus.class, value = {"是", "否"})
    private Boolean isAllowAmount;

    @ExcelProperty(value = "款项描述", order = 11)
    private String itemDesc;

    @ExcelProperty(value = "收款方编号", order = 12)
    private String receiverId;

    @ExcelProperty(value = "收款方名称", order = 13)
    private String receiverName;

    //@ExcelProperty("顺序号")
    //private Integer seqNo;

    //@ExcelProperty(value = "创建时间", order = 14)
    //private LocalDateTime gmtCreate;

    //@ExcelProperty(value = "更新时间", order = 15)
    //private LocalDateTime gmtModified;

}
