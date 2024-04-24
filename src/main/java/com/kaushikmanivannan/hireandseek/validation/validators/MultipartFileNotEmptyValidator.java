package com.kaushikmanivannan.hireandseek.validation.validators;

import com.kaushikmanivannan.hireandseek.validation.constraints.MultipartFileNotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileNotEmptyValidator implements ConstraintValidator<MultipartFileNotEmpty, MultipartFile> {

    @Override
    public void initialize(MultipartFileNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile != null && !multipartFile.isEmpty();
    }
}
