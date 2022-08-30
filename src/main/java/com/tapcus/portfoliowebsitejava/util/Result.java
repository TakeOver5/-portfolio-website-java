package com.tapcus.portfoliowebsitejava.util;

import lombok.Data;

@Data
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result() {}

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // <E> 表示該方法是一個泛型方法，它具有帶有名稱的泛型類型參數 E，否則編譯器會認為該 E 是一種類型
    public static <E> Result<E> success() {
        return new Result(200);
    }

    public static <E> Result<E> success(String message) {
        return new Result(200, message);
    }

    public static <E> Result<E> success(E data) {
        return new Result(200, data);
    }

    public static <E> Result<E> success(String message, E data) {
        return new Result(200, message, data);
    }

    public static <E> Result<E> error(E data) {
        return new Result<>(400, data);
    }

    public static <E> Result<E> error(String message) {
        return new Result(400, message);
    }

    public static <E> Result<E> error(String message, E data) {
        return new Result(400, message, data);
    }

    public static <E> Result<E> error(int code, String message) {
        return new Result(code, message);
    }
}
