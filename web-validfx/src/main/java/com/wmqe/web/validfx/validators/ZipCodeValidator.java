package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.annotations.ZipCode;

/**
 *
 */
public class ZipCodeValidator extends RegexValidator<ZipCode> {

    public static final String REG_EXP = "^[0-9]\\d{5}";

    @Override
    public void onLoad() {

    }
    public ZipCodeValidator()  {
        super(REG_EXP);
    }
}
