package com.bobo.mp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 
 * @TableName DYNA_AMS_SUBPROD_API
 */
@TableName(value ="DYNA_AMS_SUBPROD_API")
@Data
@Component
public class DynaAmsSubprodApi implements Serializable {
    /**
     * 
     */
    @TableId(value = "ID")
    private Long id;

    /**
     * 产品编号：0-云商通、1-ISV版云商通、2-慧营销
     */
    @TableField(value = "PROD_ID")
    private String prodId;

    /**
     * 接口编号
     */
    @TableField(value = "API_ID")
    private Long apiId;

    /**
     * 
     */
    @TableField(value = "APPLICATION_ID")
    private Long applicationId;

    /**
     * 接口英文名
     */
    @TableField(value = "NAME_EN")
    private String nameEn;

    /**
     * 接口版本号
     */
    @TableField(value = "P_VERSION")
    private String pVersion;

    /**
     * SOA服务名称
     */
    @TableField(value = "NAME_SOA")
    private String nameSoa;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_USER_NAME")
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATE_USER_NAME")
    private String updateUserName;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 1是可用，0是禁用。
     */
    @TableField(value = "AVAILABLE")
    private Short available;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}