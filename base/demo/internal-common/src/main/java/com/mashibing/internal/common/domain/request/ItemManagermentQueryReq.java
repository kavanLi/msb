package com.mashibing.internal.common.domain.request;

import com.mashibing.internal.common.constant.ItemManagermentConstants;
import lombok.Data;

@Data
public class ItemManagermentQueryReq {

    /**
     * 编号（无意义唯一）主键、unsigned、单表时自增、步长为 1
     */
    private Long id;

    /**
     * 商户用户编号
     */
    private String bizUserId;

    /**
     * 应用编号
     */
    private String applicationId;

    /**
     * 科目编号
     */
    private String itemNo;

    /**
     * 父科目编号
     */
    private String parentItemNo;

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
    private Long itemPrice = 0L;

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
    private Integer status = ItemManagermentConstants.ItemStatus.ITEM_STATUS_ENABLE.getCode();

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 订单生成时间开始——结束
     */
    private String startTime;
    private String endTime;

    /**
     * 页码
     */
    private Long pageNo = 1L;

    /**
     * 页大小
     */
    private Long pageSize = 10L;


}
