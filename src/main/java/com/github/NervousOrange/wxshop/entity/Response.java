package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.User;

public class Response {
    private Boolean login;
    private User user;
    private String message;

    private Response() {
    }

    private Response(Boolean login, String message) {
        this.login = login;
        this.message = message;
    }

    private Response(Boolean login, User user) {
        this.login = login;
        this.user = user;
    }

    public static Response notLoggedResponse(String message) {
        return new Response(false, message);
    }

    public static Response emptyResponse() {
        return new Response();
    }

    public static Response loggedResponse(User user) {
        return new Response(true, user);
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
