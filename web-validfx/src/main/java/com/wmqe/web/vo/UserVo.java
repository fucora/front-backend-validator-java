package com.wmqe.web.vo;

import com.wmqe.web.validfx.annotations.*;
import com.wmqe.web.validfx.models.Severity;

import java.io.Serializable;

@ValidationModel("user")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 9187584524512835680L;

    @NotEmpty
    @Length(max = 15,min = 5,payload = Severity.Info.class)
    private String userId;
    @Email
    private String email;
    @Mobile
    private String phone;

    private String[] hobbies;

    private String gender;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
