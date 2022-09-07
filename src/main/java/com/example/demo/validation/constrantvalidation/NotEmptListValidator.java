package com.example.demo.validation.constrantvalidation;

import com.example.demo.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptListValidator implements ConstraintValidator<NotEmptyList, List> {
    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        return list != null && !list.isEmpty() ;
    }

}
