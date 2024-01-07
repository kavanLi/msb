package com.example.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 15:58
 * To change this template use File | Settings | File and Code Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapperTestVO {
    private Long uuid;
    private String name;
    private Integer age;
    private String email;
    private String sex;
    private String money;

}
