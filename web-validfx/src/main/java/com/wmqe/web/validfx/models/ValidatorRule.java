package com.wmqe.web.validfx.models;

public class ValidatorRule {
    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则失败的消息
     */
    private String message;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
