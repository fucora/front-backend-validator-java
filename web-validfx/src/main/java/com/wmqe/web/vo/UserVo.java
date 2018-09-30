package com.wmqe.web.vo;

import com.wmqe.web.validfx.annotations.*;
import com.wmqe.web.validfx.models.Severity;

import java.io.Serializable;
import java.util.List;

@ValidationModel("user")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 9187584524512835680L;

    @NotEmpty
    @Length(max = 15,min = 5,payload = Severity.Info.class)
    @ChsChars
    private String userId;
    @Email
    @Length(max = 12,min = 8)
    private String email;
    @Mobile
    private String phone;
    @ZipCode
    private String zipCode;

    @Size(min = 1, message = "至少有{min}个爱好")
    private List<String> hobbies;

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

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
