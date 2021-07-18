package com.github.NervousOrange.wxshop.common.exception;

public class ShopNotAuthorizedException extends RuntimeException{
    public ShopNotAuthorizedException() {
    }

    public ShopNotAuthorizedException(String message) {
        super(message);
    }

    public ShopNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
