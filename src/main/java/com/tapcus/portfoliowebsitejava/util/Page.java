package com.tapcus.portfoliowebsitejava.util;


import lombok.Data;

@Data
public class Page<T> extends Result<T>{
    private Integer limit;
    private Integer offset;
    private Integer total;

    public Page(int code, String message, T data) {
        super(code, message, data);
    }
}
