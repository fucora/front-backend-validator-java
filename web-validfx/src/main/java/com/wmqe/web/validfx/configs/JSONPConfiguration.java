package com.wmqe.web.validfx.configs;

import com.wmqe.web.controllers.ValidatorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;


@ControllerAdvice(basePackageClasses = {ValidatorController.class})
public class JSONPConfiguration extends JsonpResponseBodyAdvice {
    public JSONPConfiguration(){
        super("callback","jsonp");
    }
}