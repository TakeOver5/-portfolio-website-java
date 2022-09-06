package com.tapcus.portfoliowebsitejava.dao;

public interface ArticleDao {

    Integer createArticle(Integer memberId,
                          String title,
                          String introduction,
                          String content,
                          String coverUrl,
                          String git_file_path);
}
