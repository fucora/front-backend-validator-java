package com.wmqe.web.validfx.annotations;

import com.wmqe.web.validfx.validators.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy={MobileValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Mobile {
    String value() default "";
    
    String message() default "手机格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
