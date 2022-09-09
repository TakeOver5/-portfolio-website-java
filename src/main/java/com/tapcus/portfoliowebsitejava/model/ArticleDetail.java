package com.tapcus.portfoliowebsitejava.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleDetail {
    private Integer articleId;
    private String title;
    private String introduction;
    private String content;
    private String coverPath;
    private Date lastModifiedDate;
    private String gitFilePath;
    private byte[] avatar;
    private String name;
    private List<MessageDetail> messageDetail;
}
