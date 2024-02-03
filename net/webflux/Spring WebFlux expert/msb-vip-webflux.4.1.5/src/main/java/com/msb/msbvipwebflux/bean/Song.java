package com.msb.msbvipwebflux.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Song {
    private Integer id;
    private String name;
    private String artist;
    private String album;
}
