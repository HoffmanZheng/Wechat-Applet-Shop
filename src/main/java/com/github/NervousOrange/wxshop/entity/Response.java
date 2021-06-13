package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.User;

public class Response {
    private Boolean login;
    private User user;
    private String message;

    // 和 JSON 协同，需要一个空的构造器
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

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

}
