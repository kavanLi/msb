package com.msb.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Student {
    @Id
    private Long id;

    private String code;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String address;

    private String remark;
    private boolean active;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
