package com.wmqe.web.validfx.annotations;


import com.wmqe.web.validfx.validators.SizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 长度校验注解
 *
 * @author MagicYang
 * @date 2018年9月6日
 */
@Documented
@Constraint(validatedBy={SizeValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Size {

    int min() default 0;

    int max() default Integer.MAX_VALUE;
    
    String message() default "列表大小不符合要求";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
