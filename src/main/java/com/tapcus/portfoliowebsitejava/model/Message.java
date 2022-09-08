package com.tapcus.portfoliowebsitejava.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Integer messageId;
    private String content;
    private Date createdDate;
    private Integer memberId;
    private Integer articleId;
}
