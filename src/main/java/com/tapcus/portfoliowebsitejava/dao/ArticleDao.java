package com.tapcus.portfoliowebsitejava.dao;

import com.tapcus.portfoliowebsitejava.model.Article;
import com.tapcus.portfoliowebsitejava.model.ArticleDetail;
import com.tapcus.portfoliowebsitejava.model.MessageDetail;

import java.util.List;

public interface ArticleDao {

    Integer createArticle(Integer memberId,
                          String title,
                          String introduction,
                          String content,
                          String coverUrl,
                          String git_file_path);

    Integer createMessage(Integer articleId, Integer memberId, String content);

    List<Article> getArticles(Integer limit, Integer offset);

    Integer countArticle();

    ArticleDetail getArticle(Integer articleId);

    List<MessageDetail> getMessage(Integer articleId);
}
