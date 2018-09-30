package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.annotations.Mobile;

/**
 *
 */
public class MobileValidator extends RegexValidator<Mobile> {

    public static final String REG_EXP = "^1[3|4|5|8|9]\\d{9}$";

    @Override
    public void onLoad() {

    }
    public MobileValidator()  {
        super(REG_EXP);
    }
}
