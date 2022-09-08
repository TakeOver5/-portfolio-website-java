package com.tapcus.portfoliowebsitejava.rowmapper;

import com.tapcus.portfoliowebsitejava.model.Article;
import com.tapcus.portfoliowebsitejava.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article> {
    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {

        Article article = new Article();
        article.setArticleId(rs.getInt("article_id"));
        article.setTitle(rs.getString("title"));
        article.setIntroduction(rs.getString("introduction"));
        article.setContent(rs.getString("content"));
        article.setCoverPath(rs.getString("cover_path"));
        article.setCreatedDate(rs.getTimestamp("created_date"));
        article.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
        article.setGitFilePath("git_file_path");
        article.setViewable(rs.getBoolean("viewable"));
        article.setMemberId(rs.getInt("member_id"));

        return article;
    }
}
