package com.mashibing.internal.common.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

/**
 * 
 * @TableName DYNA_AMS_ORGPROVISIONS
 */
@TableName(value ="DYNA_AMS_ORGPROVISIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynaAmsOrgprovisions implements Serializable {
    /**
     * ??
     */
    @TableId
    @Id
    private Long id;

    /**
     * ?ID
     */
    private Long domainid;

    /**
     * ??ID
     */
    private Long orgId;

    /**
     * ??
     */
    private String orgLabel;

    /**
     * ???
     */
    private String orgno;

    /**
     * ????
     */
    private Long manageModel;

    /**
     * ????
     */
    private String accountName;

    /**
     * ????
     */
    private String accountNo;

    /**
     * ???
     */
    private String bankName;

    /**
     * ??
     */
    private String bankCode;

    /**
     * 
     */
    private Integer isTransformDaifu;

    /**
     * 
     */
    private String daifuMerchantId;

    /**
     * ????ID
     */
    private Long manageBankId;

    /**
     * ????
     */
    private String manageBankLabel;

    /**
     * ??????
     */
    private String manageBankNo;

    /**
     * ??????????
     */
    private String platNo;

    /**
     * ?????
     */
    private String serverNo;

    /**
     * ???????
     */
    private String signPublicCertsStr;

    /**
     * ???????
     */
    private String signPrivateCertsStr;

    /**
     * ???????
     */
    private String enPublicCertsStr;

    /**
     * ???????
     */
    private String dePrivateCertsStr;

    /**
     * ????????????
     */
    private Long tltRefundAutoTransfer;

    /**
     * ??????????
     */
    private String targetTltMerchantId;

    /**
     * ?????
     */
    private Long tltOverdraftAmount;

    /**
     * ?????? ????
     */
    private Long transferBizType;

    /**
     * ???????
     */
    private Integer issendbank;

    /**
     * ???????
     */
    private Long paymentMaxAmount;

    /**
     * ??????
     */
    private Long paymentMinAmount;

    /**
     * ????
     */
    private String bankProvince;

    /**
     * ????
     */
    private String bankCity;

    /**
     * 
     */
    private Date createDatetime;

    /**
     * 
     */
    private String createUserId;

    /**
     * 
     */
    private Date lastUpdateDatetime;

    /**
     * 
     */
    private String updateUserId;

    /**
     * 开户机构类型
     */
    private Integer acctOrgType;

    /**
     * 
     */
    private String allowPulloutBegintime;

    /**
     * 
     */
    private String allowPulloutEndtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}