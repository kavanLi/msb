package com.mashibing.internal.common.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-02 00:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class ListMaxSizeValidator implements ConstraintValidator<ListMaxSize, List<?>> {

    private int maxSize;

    @Override
    public void initialize(ListMaxSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        return list == null || list.size() <= maxSize;
    }


}
