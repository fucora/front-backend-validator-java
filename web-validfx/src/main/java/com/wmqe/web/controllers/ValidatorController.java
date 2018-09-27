package com.wmqe.web.controllers;

import com.alibaba.fastjson.JSONObject;
import com.wmqe.web.validfx.models.ValidatorInfo;
import com.wmqe.web.validfx.validators.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private List<ValidatorInfo> getValidatorInfos(String name){
        List<ValidatorInfo> validatorInfoList = new ArrayList<>();
        ValidatorInfo userId = new ValidatorInfo();
        userId.setPropertyName("userId");
        userId.setRequired(true);
        ValidatorInfo email = new ValidatorInfo();
        email.setPropertyName("email");
        email.setRequired(true);
        email.setRuleName(new String[]{"email"});

        validatorInfoList.add(userId);
        validatorInfoList.add(email);
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
