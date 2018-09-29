package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.annotations.StrongPassword;

/**
 *
 */
public class StrongPasswordValidator extends RegexValidator<StrongPassword> {

    public static final String REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    @Override
    public void onLoad() {

    }
    public StrongPasswordValidator()  {
        super(REGEX);
    }
}
