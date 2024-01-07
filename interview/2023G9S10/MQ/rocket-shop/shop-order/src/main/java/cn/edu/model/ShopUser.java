package cn.edu.model;

import java.util.Date;

public class ShopUser {
    private Long userId;

    private String userName;

    private String userPassword;

    private String userMobile;

    private Date userRegTime;

    private Long userMoney;

    public ShopUser(Long userId, String userName, String userPassword, String userMobile, Date userRegTime, Long userMoney) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userMobile = userMobile;
        this.userRegTime = userRegTime;
        this.userMoney = userMoney;
    }

    public ShopUser() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public Date getUserRegTime() {
        return userRegTime;
    }

    public void setUserRegTime(Date userRegTime) {
        this.userRegTime = userRegTime;
    }

    public Long getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(Long userMoney) {
        this.userMoney = userMoney;
    }
}