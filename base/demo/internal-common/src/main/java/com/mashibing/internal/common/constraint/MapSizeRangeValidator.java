package com.mashibing.internal.common.constraint;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-02 00:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class MapSizeRangeValidator implements ConstraintValidator<MapSizeRange, Map <String, Object>> {

    private int minSize;
    private int maxSize;

    @Override
    public void initialize(MapSizeRange constraintAnnotation) {
        this.minSize = constraintAnnotation.min();
        this.maxSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Map<String, Object> map, ConstraintValidatorContext context) {
        if (map == null) {
            return false;
        }

        int mapSize = map.size();

        if (maxSize == -1) {
            return mapSize >= minSize;
        } else {
            return mapSize >= minSize && mapSize <= maxSize;
        }
    }

}
