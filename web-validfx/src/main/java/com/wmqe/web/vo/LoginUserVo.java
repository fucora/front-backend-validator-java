package com.wmqe.web.vo;

import com.wmqe.web.validfx.annotations.*;
import com.wmqe.web.validfx.models.Severity;

@ValidationModel("loginUser")
public class LoginUserVo {
    @NotEmpty
    @Length(max = 15,min = 5,payload = Severity.Info.class)
    @ChsChars
    private String userId;
    @NotEmpty
    @StrongPassword
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
