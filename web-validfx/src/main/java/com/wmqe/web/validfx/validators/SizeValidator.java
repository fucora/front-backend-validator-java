package com.wmqe.web.validfx.validators;
import com.wmqe.web.validfx.annotations.Length;

import javax.validation.ConstraintValidatorContext;

/**
 * 字符长度校验类
 */
public class SizeValidator extends BaseValidator<Length> {
    private final String PARAM_NAME_MIN = "min";
    private final String PARAM_NAME_MAX = "max";
    @Override
    public void onLoad() {

    }

    @Override
    public void onInitialize(Length parameters) {
        int min = parameters.min();
        int max = parameters.max();
        setParameter(PARAM_NAME_MIN, min);
        setParameter(PARAM_NAME_MAX, max);
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return true;
        }
        int min = getParameterInt(PARAM_NAME_MIN, 0);
        int max = getParameterInt(PARAM_NAME_MAX, Integer.MAX_VALUE);
        int valueLength = value.length();
        if(max == Integer.MAX_VALUE && min > 0 && valueLength < min) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("长度至少为"+min+"个字符").addConstraintViolation();
            return false;
        } else if(min == 0 && max < Integer.MAX_VALUE && valueLength > min) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("长度不能超过"+max+"个字符").addConstraintViolation();
            return false;
        } else if(min > 0 && max < Integer.MAX_VALUE && (valueLength > max || valueLength < min)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("长度必须在"+min+"至"+max+"的范围").addConstraintViolation();
            return false;
        }

        return true;
    }

}
