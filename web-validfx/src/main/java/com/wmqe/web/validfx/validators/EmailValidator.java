package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.annotations.Email;
import com.wmqe.web.validfx.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 邮箱地址校验类
 *
 * @author MagicYang
 * @date 2018年9月6日
 */
public class EmailValidator implements ConstraintValidator<Email, String> {
    
    private static final String REGEX_EMAIL = "^[\\w-]+@[\\w-]+(\\.[\\w-]+)+$";
    
    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtil.isNotEmpty(value) && !Pattern.matches(REGEX_EMAIL, value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("格式不正确").addConstraintViolation();
            return false;
        }
        return true;
    }

}
