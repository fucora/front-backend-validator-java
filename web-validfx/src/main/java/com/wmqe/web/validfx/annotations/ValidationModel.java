package com.wmqe.web.validfx.annotations;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidationModel {
    String value() default "";
}
