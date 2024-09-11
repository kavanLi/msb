package com.msb.es.entity;

import com.msb.es.dto.Document;
import com.msb.es.dto.EsDataId;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "msb_car_info")
@Data
public class MsbCarInfo implements Serializable {
    @EsDataId
    @Field(type = FieldType.Keyword)
    private Integer id;
    @Field(type = FieldType.Integer)
    private Integer uniacid;
    @Field(type = FieldType.Integer)
    private Integer brand_id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Text)
    private String pic_url;
    @Field(type = FieldType.Integer)
    private Integer status;
    @Field(type = FieldType.Integer)
    private Integer sort;
    @Field(type = FieldType.Integer)
    private Integer create_time;
}
