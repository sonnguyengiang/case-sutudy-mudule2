package com.son.show.login;

import java.io.Serializable;

public class Login implements Serializable{
    private String userName;
    private String account;
    private String password;

    public Login() {
    }

    public Login(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }


    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
