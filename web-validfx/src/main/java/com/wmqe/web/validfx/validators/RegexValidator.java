package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

public class RegexValidator<A extends Annotation> implements ConstraintValidator<A, String> {

    private String regex = null;
    private String message = null;

    public RegexValidator() {
    }

    public RegexValidator(String regex, String message) {
        if(StringUtil.isEmpty(regex))
            throw new NullPointerException("regex is null");
        if( StringUtil.isEmpty(message))
            throw new NullPointerException("message is null");

        this.regex = regex;
        this.message = message;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void initialize(A constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtil.isNotEmpty(value) && !Pattern.matches(this.regex, value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
