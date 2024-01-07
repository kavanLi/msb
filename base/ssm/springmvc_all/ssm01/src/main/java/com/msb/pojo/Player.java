package com.msb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Player implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private String nickname;
    private String photo;
    private String filetype;
}
