package com.wmqe.web.validfx.models;

public class ValidatorRule extends ParameterMap{
    /**
     * 规则名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 规则失败的消息
     */
    private String message;

//    public String getRuleName() {
//        return ruleName;
//    }
//
//    public void setRuleName(String ruleName) {
//        this.ruleName = ruleName;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
