package com.tapcus.portfoliowebsitejava.dao.impl;

import com.tapcus.portfoliowebsitejava.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ArticleDaoImpl implements ArticleDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createArticle(Integer memberId, String title, String introduction, String content, String coverUrl, String git_file_path) {
        String sql = "INSERT INTO article(title, introduction, content, cover_path, git_file_path, member_id) " +
                "VALUES (:title, :introduction, :content, :cover_path, :git_file_path, :member_id)";

        Map<String, Object> map = new HashMap<>();

        map.put("title", title);
        map.put("introduction", introduction);
        map.put("content", content);
        map.put("cover_path", coverUrl);
        map.put("git_file_path", git_file_path);
        map.put("member_id", memberId);

        // 取得自增鍵
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int articleId = keyHolder.getKey().intValue();

        return articleId;
    }
}
