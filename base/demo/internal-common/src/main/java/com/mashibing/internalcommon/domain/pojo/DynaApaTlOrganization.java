package com.mashibing.internalcommon.domain.pojo;

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
 * ?????-???
 * @TableName DYNA_APA_TL_ORGANIZATION
 */
@TableName(value ="DYNA_APA_TL_ORGANIZATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynaApaTlOrganization implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * ?????
     */
    private String orgCode;

    /**
     * ?????
     */
    private String orgName;

    /**
     * ?????
     */
    private Integer orgLevel;

    /**
     * ?????
     */
    private Long parentOrgId;

    /**
     * ??????
     */
    private String orgContacts;

    /**
     * ???????
     */
    private String orgTelephone;

    /**
     * ???????
     */
    private String orgMobile;

    /**
     * ?????
     */
    private String orgEmail;

    /**
     * 拼音
     */
    private String pinyinName;

    /**
     * ？？？？？？
     */
    private String createUserName;

    /**
     * ？？？？？？
     */
    private String updateUserName;

    /**
     * ？？？？？？？？
     */
    private Date createTime;

    /**
     * ？？？？？？？？
     */
    private Date updateTime;

    /**
     * 所属分公司编号（收银宝）
     */
    private String branchCode;

    /**
     * 所属分公司名称（收银宝）
     */
    private String branchName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}