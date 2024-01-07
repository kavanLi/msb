package com.example.demo.utils;

import com.example.demo.domain.entity.MapperTestEO;
import com.example.demo.domain.model.MapperTestVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 15:57
 * To change this template use File | Settings | File and Code Templates.
 */
public class EntityToModelConverter {
    public static MapperTestVO convertEntityToModel(MapperTestEO mapperTestEO) {
        ModelMapper modelMapper = new ModelMapper();

        // 创建 PropertyMap 对象，指定源对象和目标对象的属性对应关系
        PropertyMap<MapperTestEO, MapperTestVO> propertyMap = new PropertyMap <MapperTestEO, MapperTestVO>() {
            @Override
            protected void configure() {
                // 使用 @PropertyName 注解指定源对象和目标对象的属性对应关系
                //map().setModelField(source.getEntityField());
                // 可以添加更多的映射关系
            }
        };

        // 应用 PropertyMap 对象
        modelMapper.addMappings(propertyMap);
        // 执行映射
        MapperTestVO model = modelMapper.map(mapperTestEO, MapperTestVO.class);
        return model;
    }

}
