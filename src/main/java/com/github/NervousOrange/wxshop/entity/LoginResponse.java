package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.User;

public class LoginResponse {

    private Boolean login;
    private User user;
    private String message;

    // 和 JSON 协同，需要一个空的构造器
    private LoginResponse() {
    }

    private LoginResponse(Boolean login, String message) {
        this.login = login;
        this.message = message;
    }

    private LoginResponse(Boolean login, User user) {
        this.login = login;
        this.user = user;
    }

    public static LoginResponse notLoggedResponse(String message) {
        return new LoginResponse(false, message);
    }

    public static LoginResponse emptyResponse() {
        return new LoginResponse();
    }

    public static LoginResponse loggedResponse(User user) {
        return new LoginResponse(true, user);
    }

    public Boolean getLogin() {
        return login;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

}
