package com.bobo.mp.domain.pojo;

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

/**
 * 
 * @TableName DYNA_AMS_ORGANIZATION
 */
@TableName(value ="DYNA_AMS_ORGANIZATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynaAmsOrganization implements Serializable {

    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private Long domainid;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String codeno;

    /**
     * 
     */
    private Long memberId;

    /**
     * 
     */
    private String memberLabel;

    /**
     * 
     */
    private Long accountTypeId;

    /**
     * 
     */
    private String accountTypeLabel;

    /**
     * 
     */
    private Long companyId;

    /**
     * 
     */
    private String companyLabel;

    /**
     * 
     */
    private String sysid;

    /**
     * 
     */
    private String publicKey;

    /**
     * 
     */
    private String privateKey;

    /**
     * 
     */
    private String allowedIp;

    /**
     * 
     */
    private Long timerange;

    /**
     * 
     */
    private Long state;

    /**
     * 
     */
    private String encryption;

    /**
     * 
     */
    private Long accessMethod;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private Long transactionType;

    /**
     * 
     */
    private String allowedip;

    /**
     * 
     */
    private String country;

    /**
     * 
     */
    private String locality;

    /**
     * 
     */
    private String organization;

    /**
     * 
     */
    private String organizationunit;

    /**
     * 
     */
    private String orgState;

    /**
     * 
     */
    private String memberUuid;

    /**
     * 
     */
    private String certPassword;

    /**
     * 
     */
    private String cert;

    /**
     * 
     */
    private Long appType;

    /**
     * 
     */
    private String ipcPath;

    /**
     * 
     */
    private String licensePath;

    /**
     * 
     */
    private String creditPath;

    /**
     * 
     */
    private String webUrl;

    /**
     * 
     */
    private Integer isWithdrawSafeCard;

    /**
     * 
     */
    private Integer isWithdrawtypeT0;

    /**
     * 
     */
    private Integer isWithdrawtypeT1;

    /**
     * 
     */
    private Long withdrawreservemodel;

    /**
     * 
     */
    private String appUrl;

    /**
     * 
     */
    private Long manageBankId;

    /**
     * 
     */
    private String manageBankLabel;

    /**
     * 
     */
    private String manageBankNo;

    /**
     * 
     */
    private String platBusiType;

    /**
     * 
     */
    private String platAddress;

    /**
     * 
     */
    private String platIp;

    /**
     * 
     */
    private String companyAttr;

    /**
     * 
     */
    private Date companyEstablishDate;

    /**
     * 
     */
    private String platSource;

    /**
     * 
     */
    private String portalWithdrawType;

    /**
     * 
     */
    private Long autoAudit;

    /**
     * 
     */
    private Long fmCreateprinid;

    /**
     * 
     */
    private String fmCreateprinname;

    /**
     * 
     */
    private Long fmUpdateprinid;

    /**
     * 
     */
    private String fmUpdateprinname;

    /**
     * 
     */
    private Date fmCreatetime;

    /**
     * 
     */
    private Date fmUpdatetime;

    /**
     * 
     */
    private Long isTestMerchant;

    /**
     * 
     */
    private Long t1customizedTriggerHour;

    /**
     * 
     */
    private Long d0customizedIntervalTime;

    /**
     * 
     */
    private Integer isTypeT1customized;

    /**
     * 
     */
    private Integer isTypeD0customized;

    /**
     * 
     */
    private Long rechargeIntervalTime;

    /**
     * 
     */
    private Long appState;

    /**
     * 
     */
    private String serviceProvider;

    /**
     * 
     */
    private Integer fileDownloadCtrl;

    /**
     * 
     */
    private Long allFileDownloadCtrl;

    /**
     * 
     */
    private Long sybOrganizationId;

    /**
     * 
     */
    private Long source;

    /**
     * 
     */
    private Long allFileDownloadCtrlExcel;

    /**
     * 
     */
    private String portalPayType;

    /**
     * 
     */
    private String platformAppid;

    /**
     * 
     */
    private String eaccountCustid;

    /**
     * 
     */
    private Long eaccountSetNotifyUrlFlag;

    /**
     * 
     */
    private Long eaccountCustInfoSetFlag;

    /**
     * 
     */
    private Long mchtJoinModel;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 禁用类型，大小类STOP_TYPE
     */
    private Long stopType;

    /**
     * 禁用说明
     */
    private String stopContent;

    /**
     * 行业标签，大小类
     */
    private Long merchantTag;

    /**
     * 
     */
    private String riskTypes;

    /**
     * 
     */
    private Long riskLevel;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}