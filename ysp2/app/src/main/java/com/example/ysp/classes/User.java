package com.example.ysp.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userMail")
    @Expose
    private String userMail;
    @SerializedName("userPass")
    @Expose
    private String userPass;
    public User(String userID, String userEmail, String userpass) {
        this.userId = userID;
        this.userMail = userEmail;
        this.userPass = userpass;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
    public String toString() {
        return userMail;
    }

}