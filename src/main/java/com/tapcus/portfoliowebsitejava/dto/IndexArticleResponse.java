package com.tapcus.portfoliowebsitejava.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IndexArticleResponse  {
    private Integer article_id;
    private String title;
    private String introduction;
    private String cover_path;
}
