package com.tapcus.portfoliowebsitejava.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleSimple {
    private Integer articleId;
    private String title;
    private Integer memberId;
    private byte[] avatar;
    private String name;
    private String email;
    private Date lastModifiedDate;
    private boolean viewable;
}
