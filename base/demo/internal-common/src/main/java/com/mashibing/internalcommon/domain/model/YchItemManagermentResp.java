package com.mashibing.internalcommon.domain.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class YchItemManagermentResp {


    /**
     * 编号（无意义唯一）主键、unsigned、单表时自增、步长为 1
     *
     * 在一些场景下，特别是在分布式系统中或者涉及到较大的数值时，如果直接使用long类型返回给前端，可能会出现精度损失的问题。为了避免这种问题，建议使用String类型来表示超大整数，因为字符串类型不会受到精度限制，并且能够保证数据的精确性。
     *
     * JavaScript 的 Number 类型是双精度浮点数，精度有限。
     * Long 类型能表示的最大值为 2^63-1，超过 2^53 的数值可能会在转换为 Number 时丢失精度。
     *
     * 使用 String 类型返回超大整数
     *
     * 这是最简单有效的解决方案，避免精度损失问题。
     * 可以使用 @JsonFormat 注解将 Long 类型属性格式化为 String 类型。
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 商户用户编号
     */
    private String bizUserId;

    /**
     * 科目编号
     */
    private String itemNo;

    /**
     * 科目名称
     */
    private String itemName;

    /**
     * 科目描述
     */
    private String itemDesc;

    /**
     * 科目层级，1-科目类别 2-科目明细
     */
    private Integer itemTier;

    /**
     * 应用场景，1-POS款项 2-H5款项
     */
    private Integer itemScene;

    /**
     * 顺序号
     */
    private Integer seqNo;

    /**
     * 价格，单位：分
     */
    private Long itemPrice;

    /**
     * 实名标识
     */
    private Boolean isRealName;

    /**
     * 收款方编号
     */
    private String receiverId;

    /**
     * 收款方名称
     */
    private String receiverName;

    /**
     * 启用状态，1-启用 0-禁用
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间，格式：YYYY-MM-DD hh:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 更新人
     */
    private String modifiedUserName;

    /**
     * 更新时间，格式：YYYY-MM-DD hh:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

}
