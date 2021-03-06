package com.wmqe.web.validfx.annotations;


import com.wmqe.web.validfx.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy={EmailValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Email {
    /**
     *
     * @return
     */
    String message() default "Email格式不正确";

    /**
     *
     * @return
     */
    Class<?>[] groups() default {};

    /**
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}
