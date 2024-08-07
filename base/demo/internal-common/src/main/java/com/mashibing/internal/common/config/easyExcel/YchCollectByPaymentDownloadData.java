package com.mashibing.internal.common.config.easyExcel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Accessors(chain = true) 读excel不能使用这个注解，否则会解析失败
 * https://blog.51cto.com/YangPC/5483482
 */


@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
public class YchCollectByPaymentDownloadData {

    /**
     * 忽略这个字段
     */
    /**主键*/
    @ExcelIgnore
    private Long id;

    @ExcelProperty("源代收订交易场景")
    private String scene;

    @ExcelProperty("源代收订单商户订单号")
    private String bizOrderNo;

    @ExcelProperty("源代收订单云商通订单号")
    private String orderNo;

    @ExcelProperty("源代收订单收款人会员编号")
    private String receiverId;

    @ExcelProperty("源代收订交易场景")
    private String receiverName;

    @ExcelProperty("源代收订单状态")
    private String status;

    @ExcelProperty("科目款项编号")
    private String itemNo;

    @ExcelProperty("收款角色编号")
    private Long paymentId;

    @ExcelProperty("收款角色名称")
    private String paymentName;

    @ExcelProperty("源代收订单收款人会员编号")
    private String paymentReceiverId;

    @ExcelProperty("源代收订单收款人会员名称")
    private String paymentReceiverName;

    @ExcelProperty("订单金额（分）")
    private Long oriAmount;

    @ExcelProperty("分账金额（分）")
    private Long itemAmount;

    @ExcelProperty("渠道手续费（分）")
    private Long channelFee;

    @ExcelProperty("手续费（分）")
    private Long itemFee;

    @ExcelProperty("退款金额（分）")
    private Long refundAmount;

    @ExcelProperty("退款时间")
    private Date refundTime;

    @ExcelProperty("退款商户订单号")
    private String refundBizOrderNo;

    @ExcelProperty("退款商户订单号")
    private String refundOrderNo;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("更新时间")
    private Date updateTime;

}
