package com.msb.pojo;

import java.io.Serializable;

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
public class Depta implements Serializable {
    /*@TableField(exist = false)
    private String aaa;*/
    @TableField("deptno")
    private Integer deptno;
    private String dname;
    private String loc;

}
