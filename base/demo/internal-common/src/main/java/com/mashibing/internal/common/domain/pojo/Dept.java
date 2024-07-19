package com.mashibing.internal.common.domain.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("dept")
public class Dept implements Serializable {
    /*@TableField(exist = false)
    private String aaa;*/
    @TableField("deptno")
    private Integer deptno;

    @NotBlank(message = "dname不能为空")
    private String dname;
    private String loc;

}
