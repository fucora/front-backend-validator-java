package com.wmqe.web.controllers;

import com.alibaba.fastjson.JSONObject;
import com.wmqe.web.validfx.annotations.Email;
import com.wmqe.web.validfx.annotations.NotEmpty;
import com.wmqe.web.validfx.models.ValidatorInfo;
import com.wmqe.web.validfx.utils.Scanner;
import com.wmqe.web.validfx.validators.EmailValidator;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/validators")
public class ValidatorController {

    @GetMapping("/rules")
    public ResponseEntity getRules() {
//        List<ValidatorRule> ruleList = new ArrayList<>();
//
//        EmailValidatorRule emailRule = new EmailValidatorRule();
//        emailRule.setRuleName("email");
//        emailRule.setMessage("Email格式错误");
//        emailRule.setRegex(EmailValidator.REGEX_EMAIL);
//
//        ruleList.add(emailRule);

        List<JSONObject> ruleList = new ArrayList<>();
        JSONObject emailRule = new JSONObject();
        emailRule.put("ruleName","email");
        emailRule.put("message","Email格式错误");
        emailRule.put("regex",EmailValidator.REGEX_EMAIL);
        ruleList.add(emailRule);

        return new ResponseEntity<Object>(ruleList, HttpStatus.OK);
    }

    private Class<? extends ConstraintValidator<?,?>>[] getValidatedBy(Class<? extends Annotation> annotationType){
        Constraint constraint = annotationType.getAnnotation(Constraint.class);
        if(constraint == null)
            return null;

        Class<? extends ConstraintValidator<?,?>>[] validatedBy = constraint.validatedBy();
        if(validatedBy.length == 0)
            return null;

        return validatedBy;
    }

    private ValidatorInfo getValidatorInfo(Field field){
        Annotation[] annotations = field.getAnnotations();

        if(annotations.length == 0)
            return null;

        boolean required = false;
        List<String> ruleList = new ArrayList<>();

        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            Class annotationType = annotation.annotationType();
            if(annotationType.isAssignableFrom(NotEmpty.class)){
                required = true;
                continue;
            }
            Class<? extends ConstraintValidator<?,?>>[] validatedBy = getValidatedBy(annotationType);
            if(validatedBy != null){
                String annotationName = annotationType.getSimpleName();
                annotationName = annotationName.substring(0,1).toLowerCase() + annotationName.substring(1);
                ruleList.add(annotationName);
            }
        }

        if(ruleList.size() == 0)
            return null;

        String[] rules = new String[ruleList.size()];
        ruleList.toArray(rules);

        ValidatorInfo validatorInfo = new ValidatorInfo();
        validatorInfo.setPropertyName(field.getName());
        validatorInfo.setRequired(required);
        validatorInfo.setRuleName(rules);

        return validatorInfo;
    }

    private List<ValidatorInfo> getValidatorInfos(String name){

        Class<?> cls = Scanner.getValidationModels(name);
        if(cls == null)
            return null;

        Field[] fields = cls.getDeclaredFields();
//        Method[] methods = cls.getMethods();
//        for (int i = 0; i < methods.length ; i++) {
//            Method method = methods[i];
//
//            //AnnotationUtils.findAnnotation()
//
//        }


        List<ValidatorInfo> validatorInfoList = new ArrayList<>();
        for (int i = 0; i < fields.length ; i++) {
            Field field = fields[i];
            ValidatorInfo vi = getValidatorInfo(field);
            if(vi == null){
                continue;
            }
            validatorInfoList.add(vi);
        }
//        ValidatorInfo userId = new ValidatorInfo();
//        userId.setPropertyName("userId");
//        userId.setRequired(true);
//        ValidatorInfo email = new ValidatorInfo();
//        email.setPropertyName("email");
//        email.setRequired(true);
//        email.setRuleName(new String[]{"email"});
//
//        validatorInfoList.add(userId);
//        validatorInfoList.add(email);
        return validatorInfoList;
    }

    @GetMapping("/models/{models}")
    public ResponseEntity getModels(@PathVariable String models) {
        String[] groups = models.split(",");
        List<JSONObject> modelList = new ArrayList<>();
        for (int i = 0; i < groups.length; i++) {
            String group = groups[i];
            String[] infos = group.split(":");
            JSONObject info = new JSONObject();
            info.put("form",infos[0]);
            info.put("items",getValidatorInfos(infos[1]));
            modelList.add(info);
        }


        return new ResponseEntity<Object>(modelList, HttpStatus.OK);
    }
}
