package com.kaushikmanivannan.hireandseek.validation.constraints;

import com.kaushikmanivannan.hireandseek.validation.validators.MultipartFileNotEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MultipartFileNotEmptyValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface MultipartFileNotEmpty {
    String message() default "The file must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
