package com.tapcus.portfoliowebsitejava.rowmapper;

import com.tapcus.portfoliowebsitejava.model.Article;
import com.tapcus.portfoliowebsitejava.model.ArticleDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleDetailRowMapper implements RowMapper<ArticleDetail> {
    @Override
    public ArticleDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

        ArticleDetail ad = new ArticleDetail();


        ad.setArticleId(rs.getInt("article_id"));
        ad.setTitle(rs.getString("title"));
        ad.setIntroduction(rs.getString("introduction"));
        ad.setContent(rs.getString("content"));
        ad.setCoverPath(rs.getString("cover_path"));
        ad.setCreatedDate(rs.getTimestamp("created_date"));
        ad.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
        ad.setGitFilePath(rs.getString("git_file_path"));
        ad.setMemberId(rs.getInt("member_id"));
        ad.setAvatar(rs.getBytes("avatar"));
        ad.setName(rs.getString("name"));

        return ad;
    }
}
