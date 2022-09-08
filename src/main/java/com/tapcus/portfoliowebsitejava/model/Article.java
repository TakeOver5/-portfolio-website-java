package com.tapcus.portfoliowebsitejava.model;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private Integer articleId;
    private String title;
    private String introduction;
    private String content;
    private String coverPath;
    private Date createdDate;
    private Date lastModifiedDate;
    private String filePath;
    private String gitFilePath;
    private Boolean viewable;
    private Integer memberId;

}
