package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.annotations.ChsChars;

/**
 *
 */
public class ChsCharsValidator extends RegexValidator<ChsChars> {

    public static final String REGEX = "^[\\u0391-\\uFFE5]+$";

    @Override
    public void onLoad() {

    }
    public ChsCharsValidator()  {
        super(REGEX);
    }
}
