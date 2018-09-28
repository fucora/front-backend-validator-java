package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.annotations.Mobile;

/**
 *
 */
public class MobileValidator extends RegexValidator<Mobile> {

    public static final String REGEX = "^1[3|4|5|8|9]\\d{9}$";

    @Override
    public void onLoad() {

    }
    public MobileValidator()  {
        super(REGEX, "手机号码格式不正确");
    }
}
