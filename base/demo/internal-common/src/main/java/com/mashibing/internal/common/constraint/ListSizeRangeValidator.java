package com.mashibing.internal.common.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-02 00:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class ListSizeRangeValidator implements ConstraintValidator<ListSizeRange, List<?>> {


    private int minSize;
    private int maxSize;

    @Override
    public void initialize(ListSizeRange constraintAnnotation) {
        this.minSize = constraintAnnotation.min();
        this.maxSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        if (list == null) {
            return false;
        }

        int listSize = list.size();

        if (maxSize == -1) {
            return listSize >= minSize;
        } else {
            return listSize >= minSize && listSize <= maxSize;
        }
    }


}
