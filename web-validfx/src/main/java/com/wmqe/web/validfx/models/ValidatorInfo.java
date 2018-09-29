package com.wmqe.web.validfx.models;

public class ValidatorInfo {
    /**
     * 属性名称
     */
    private String propertyName;
    /**
     * 规则名称
     */
    private ValidatorRule[] rules;
    /**
     * 是否必须
     */
    private boolean required;

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public ValidatorRule[] getRules() {
        return rules;
    }

    public void setRules(ValidatorRule[] rules) {
        this.rules = rules;
    }

    //
//    public String[] getRuleName() {
//        return ruleName;
//    }
//
//    public void setRuleName(String[] ruleName) {
//        this.ruleName = ruleName;
//    }
}
