package com.wmqe.web.validfx.annotations;

import com.wmqe.web.validfx.validators.StrongPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy={StrongPasswordValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface StrongPassword {
    /**
     *
     * @return
     */
    String message() default "密码要同时含有数字和字母，且长度要在8-16位之间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
