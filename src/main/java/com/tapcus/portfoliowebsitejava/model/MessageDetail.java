package com.tapcus.portfoliowebsitejava.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDetail {
    private Integer messageId;
    private String content;
    private Date createdDate;
    private byte[] avatar;
    private String name;
}
