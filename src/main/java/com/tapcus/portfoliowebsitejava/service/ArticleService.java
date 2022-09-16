package com.tapcus.portfoliowebsitejava.service;

import com.tapcus.portfoliowebsitejava.model.Article;
import com.tapcus.portfoliowebsitejava.model.ArticleDetail;
import com.tapcus.portfoliowebsitejava.model.ArticleSimple;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

public interface ArticleService {
    Integer uploadArticle(Integer memberId,
                          String title,
                          String introduction,
                          String content,
                          MultipartFile cover,
                          String git_file_path) throws IOException;

    Integer uploadMessage(Integer articleId, Integer memberId, String content);

    List<Article> getArticles(Integer limit, Integer offset);

    Integer countArticle();

    Integer countArticleAll();

    ArticleDetail getArticle(Integer articleId);

    List<ArticleSimple> getArticlesSimple(Integer limit, Integer offset);

    void setViewable(Integer articleId, Integer view);

    void deleteArticle(Integer articleId) throws IOException;
}
