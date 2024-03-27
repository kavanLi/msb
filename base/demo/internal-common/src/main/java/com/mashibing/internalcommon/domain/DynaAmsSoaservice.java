package com.mashibing.internalcommon.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName DYNA_AMS_SOASERVICE
 */
@TableName(value ="DYNA_AMS_SOASERVICE")
@Data
public class DynaAmsSoaservice implements Serializable {
    /**
     * 
     */
    @TableId(value = "ID")
    private Long id;

    /**
     * 
     */
    @TableField(value = "DOMAINID")
    private Long domainid;

    /**
     * 
     */
    @TableField(value = "CODENO")
    private String codeno;

    /**
     * 
     */
    @TableField(value = "NAME_CH")
    private String nameCh;

    /**
     * 
     */
    @TableField(value = "NAME_EN")
    private String nameEn;

    /**
     * 
     */
    @TableField(value = "P_VERSION")
    private String pVersion;

    /**
     * 
     */
    @TableField(value = "NAME_SOA")
    private String nameSoa;

    /**
     * 接口类型 0-标准接口    1-非标接口
     */
    @TableField(value = "TYPE_SOA")
    private Long typeSoa;

    /**
     * ？？？？？？
     */
    @TableField(value = "CREATE_USER_NAME")
    private String createUserName;

    /**
     * ？？？？？？
     */
    @TableField(value = "UPDATE_USER_NAME")
    private String updateUserName;

    /**
     * ？？？？？？？？
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    /**
     * ？？？？？？？？
     */
    @TableField(value = "UPDATE_TIME")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}