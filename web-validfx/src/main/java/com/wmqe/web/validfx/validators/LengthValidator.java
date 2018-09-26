package com.wmqe.web.validfx.validators;
import com.wmqe.web.validfx.annotations.Length;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 字符长度校验类
 *
 * @author MagicYang
 * @date 2018年9月5日
 */
public class LengthValidator implements ConstraintValidator<Length, String> {
    
    private int min;
    private int max;

    @Override
    public void initialize(Length parameters) {
        min = parameters.min();
        max = parameters.max();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        if ( value == null ) {
            return true;
        }
        
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
