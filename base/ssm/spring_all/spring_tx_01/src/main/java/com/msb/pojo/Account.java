package com.msb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account implements Serializable {
    private Integer id;
    private String name;
    private Integer money;
}
