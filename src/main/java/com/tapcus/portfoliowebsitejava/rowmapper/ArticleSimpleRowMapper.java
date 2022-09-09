package com.tapcus.portfoliowebsitejava.rowmapper;

import com.tapcus.portfoliowebsitejava.model.ArticleSimple;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleSimpleRowMapper implements RowMapper<ArticleSimple> {
    @Override
    public ArticleSimple mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArticleSimple as = new ArticleSimple();

        as.setArticleId(rs.getInt("article_id"));
        as.setTitle(rs.getString("title"));
        as.setCreatedDate(rs.getTimestamp("created_date"));
        as.setViewable(rs.getBoolean("viewable"));
        as.setName(rs.getString("name"));
        as.setAvatar(rs.getBytes("avatar"));
        as.setEmail(rs.getString("email"));

        return as;
    }
}
