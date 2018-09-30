package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.utils.StringUtil;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

public abstract class RegexValidator<A extends Annotation> extends BaseItemValidator<A> {

    private final String PARAM_NAME_REGEX = "regex";
    public RegexValidator() {

    }

    @Override
    public void onLoad() {

    }

    public RegexValidator(String regex) {
        if(StringUtil.isEmpty(regex))
            throw new NullPointerException("regex is null");

        setParameter(PARAM_NAME_REGEX,regex);
    }

    @Override
    public void onInitialize(A constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = getParameterString(PARAM_NAME_REGEX);
        if(StringUtil.isNotEmpty(value) && !Pattern.matches(regex, value)) {
            buildConstraintValidatorContext(context);
            return false;
        }
        return true;
    }
}
