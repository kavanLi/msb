package com.msb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person implements Serializable {

    private String pname;

    private String page;
    private String gender;
    private String[] hobby;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private Pet pet;

    /*private List<Pet> pets;
    private Map<String,Pet> petMap;*/



}
