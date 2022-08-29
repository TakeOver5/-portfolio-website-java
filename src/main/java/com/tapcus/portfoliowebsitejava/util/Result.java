package com.tapcus.portfoliowebsitejava.util;

import lombok.Data;

@Data
public class Result<T> {

    private int code;

    private String message;
    private T data;

    /* 通用成功返回 */
    public Result<T> succ(T data) {
        return succ(200, "操作成功", data);
    }

    public Result<T> succ(int code, String msg, T data) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    /* 通用失敗返回 */
    public Result<T> fail(String msg) {
        return fail(400, msg, null);
    }

    public Result<T> fail(int code, String msg, T data) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
