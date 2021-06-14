package com.github.NervousOrange.wxshop.entity;

public class Response<T> {
    private T data;
    private String message;

    private Response(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> Response<T> of(T data, String message) {
        return new Response<>(data, message);
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
