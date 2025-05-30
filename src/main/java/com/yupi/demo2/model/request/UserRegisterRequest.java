package com.yupi.demo2.model.request;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
public class UserRegisterRequest implements Serializable {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;

    public String getPlanetCode() {
        return planetCode;
    }

    public void setPlanetCode(String planetCode) {
        this.planetCode = planetCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}
