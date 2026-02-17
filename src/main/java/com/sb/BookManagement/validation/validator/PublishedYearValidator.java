package com.sb.BookManagement.validation.validator;

import com.sb.BookManagement.validation.annotation.ValidPublishedYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class PublishedYearValidator
        implements ConstraintValidator<ValidPublishedYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        int currentYear = Year.now().getValue();

        return value >= 1000 && value <= currentYear;
    }
}
