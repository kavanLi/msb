package com.bobo.mp.domain.pojo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName DYNA_AMS_USER_OPT_LOG
 */
@TableName(value ="DYNA_AMS_USER_OPT_LOG")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynaAmsUserOptLog implements Serializable {
    /**
     * 主键自增ID
     */
    @TableId(value = "ID")
    private Long id;

    /**
     * 创建来源1BM；2门户
     */
    @TableField(value = "CREATE_SOURCE")
    private Long createSource;

    /**
     * 登陆名
     */
    @TableField(value = "LOGIN_NAME")
    private String loginName;

    /**
     * 分公司ID
     */
    @TableField(value = "COMPANY_ID")
    private Long companyId;

    /**
     * 分公司名称
     */
    @TableField(value = "COMPANY_NAME")
    private String companyName;

    /**
     * 请求ID地址
     */
    @TableField(value = "IP_ADDRESS")
    private String ipAddress;

    /**
     * 请求地址
     */
    @TableField(value = "REQUEST_URL")
    private String requestUrl;

    /**
     * 请求参数
     */
    @TableField(value = "REQUEST_PARAM")
    private String requestParam;

    /**
     * 请求时间
     */
    @TableField(value = "REQUEST_TIME")
    private Date requestTime;

    /**
     * 操作说明
     */
    @TableField(value = "OPT_NAME")
    private String optName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}