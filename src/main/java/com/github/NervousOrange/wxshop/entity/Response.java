package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.User;

public class Response {
    private Boolean login;
    private User user;
    private String message;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(Boolean login, User user) {
        this.login = login;
        this.user = user;
    }

    public Response(Boolean login, User user, String message) {
        this.login = login;
        this.user = user;
        this.message = message;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
