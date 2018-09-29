package com.wmqe.web.controllers;

import com.alibaba.fastjson.JSONObject;
import com.wmqe.web.validfx.annotations.Email;
import com.wmqe.web.validfx.annotations.NotEmpty;
import com.wmqe.web.validfx.models.ValidatorInfo;
import com.wmqe.web.validfx.models.ValidatorRule;
import com.wmqe.web.validfx.utils.Scanner;
import com.wmqe.web.validfx.utils.StringUtil;
import com.wmqe.web.validfx.utils.ValidatorUtils;
import com.wmqe.web.validfx.validators.BaseValidator;
import com.wmqe.web.validfx.validators.EmailValidator;
import com.wmqe.web.validfx.validators.RegexValidator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/validators")
public class ValidatorController {

    @GetMapping("/rules")
    public ResponseEntity getRules() {
        return new ResponseEntity<>(ValidatorUtils.getRules(), HttpStatus.OK);
    }

    @GetMapping("/models/{models}")
    public ResponseEntity getModels(@PathVariable String models) {
        return new ResponseEntity<>(ValidatorUtils.getValidatorInfos(models), HttpStatus.OK);
    }
}
