package com.sb.BookManagement.validation.annotation;


import com.sb.BookManagement.validation.validator.PublishedYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PublishedYearValidator.class)
public @interface ValidPublishedYear {

    String message() default "Invalid published year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

