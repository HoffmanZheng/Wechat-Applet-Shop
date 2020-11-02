package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.User;
import io.swagger.annotations.ApiModelProperty;

public class LoginResponse {

    @ApiModelProperty(example = "true", required = true)
    private Boolean login;
    private User user;
    private String message;

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
