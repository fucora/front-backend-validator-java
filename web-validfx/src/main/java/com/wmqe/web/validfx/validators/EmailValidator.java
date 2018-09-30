package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.annotations.Email;
import com.wmqe.web.validfx.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 *
 */
public class EmailValidator extends RegexValidator<Email> {

    public static final String REG_EXP = "^[\\w-]+@[\\w-]+(\\.[\\w-]+)+$";

    @Override
    public void onLoad() {

    }
    public EmailValidator()  {
        super(REG_EXP);
    }
}
