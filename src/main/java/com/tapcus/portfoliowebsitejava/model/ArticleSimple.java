package com.tapcus.portfoliowebsitejava.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleSimple {
    private Integer articleId;
    private String title;
    private Date createdDate;
    private boolean viewable;
    private String name;
    private byte[] avatar;
    private String email;
}
