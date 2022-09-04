package com.tapcus.portfoliowebsitejava.model;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private Integer article_id;
    private String title;
    private String introduction;
    private String content;
    private String cover_path;
    private Date created_date;
    private Date last_modified_date;
    private String file_path;
    private String git_file_path;
    private Boolean viewable;
    private Integer member_id;

}
