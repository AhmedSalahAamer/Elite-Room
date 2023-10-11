package com.example.eliteroom.Models;

public class ModelUser {

    private String userEmail;
    private String userType;
    private String userId;
    private String userName;

    public ModelUser() {}

    public ModelUser(String userEmail, String userType, String userId, String userName) {
        this.userEmail = userEmail;
        this.userType = userType;
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
