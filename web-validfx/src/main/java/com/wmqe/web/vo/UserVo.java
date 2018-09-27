package com.wmqe.web.vo;

import com.wmqe.web.validfx.annotations.Email;
import com.wmqe.web.validfx.annotations.Length;
import com.wmqe.web.validfx.annotations.NotEmpty;

import java.io.Serializable;

public class UserVo implements Serializable {
    private static final long serialVersionUID = 9187584524512835680L;

    @NotEmpty
    @Length(max=15)
    private String userId;
    @Email
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
