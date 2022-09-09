package com.tapcus.portfoliowebsitejava.dao.impl;

import com.tapcus.portfoliowebsitejava.dao.ArticleDao;
import com.tapcus.portfoliowebsitejava.model.Article;
import com.tapcus.portfoliowebsitejava.model.ArticleDetail;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.model.MessageDetail;
import com.tapcus.portfoliowebsitejava.rowmapper.ArticleDetailRowMapper;
import com.tapcus.portfoliowebsitejava.rowmapper.ArticleRowMapper;
import com.tapcus.portfoliowebsitejava.rowmapper.MemberRowMapper;
import com.tapcus.portfoliowebsitejava.rowmapper.MessageDetailRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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

    @Override
    public Integer createMessage(Integer articleId, Integer memberId, String content) {
        String sql = "INSERT INTO message(article_id, member_id, content) " +
                "VALUES (:article_id, :member_id, :content)";

        Map<String, Object> map = new HashMap<>();

        map.put("article_id", articleId);
        map.put("member_id", memberId);
        map.put("content", content);

        // 取得自增鍵
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int messageId = keyHolder.getKey().intValue();

        return messageId;
    }

    @Override
    public List<Article> getArticles(Integer limit, Integer offset) {
        String sql = "SELECT * " +
                "FROM article WHERE 1=1 AND viewable=true " +
                "LIMIT :limit OFFSET :offset";
        Map<String, Object> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        List<Article> articleList = namedParameterJdbcTemplate.query(sql, map, new ArticleRowMapper());
        return articleList;
    }

    @Override
    public Integer countArticle() {
        String sql = "SELECT COUNT(article_id) FROM article WHERE 1=1 AND viewable=true ";
        Map<String, Object> map = new HashMap<>();
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return count;
    }

    @Override
    public ArticleDetail getArticle(Integer articleId) {

        String sql = "SELECT a.article_id, a.title, a.introduction, a.content, a.cover_path, a.last_modified_date, a.git_file_path, m.avatar, m.name " +
                "FROM article as a " +
                "LEFT JOIN member as m ON a.member_id = m.member_id " +
                "WHERE a.article_id = :articleId";

        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);

        List<ArticleDetail> articleDetailsList = namedParameterJdbcTemplate.query(sql, map, new ArticleDetailRowMapper());


        if(articleDetailsList.size() > 0)
            return articleDetailsList.get(0);
        else
            return null;
    }

    @Override
    public List<MessageDetail> getMessage(Integer articleId) {

        String sql = "SELECT mg.message_id, mg.content, mg.created_date, m.avatar, m.name " +
                "FROM message as mg " +
                "LEFT JOIN member as m ON mg.member_id = m.member_id " +
                "WHERE mg.article_id = :articleId";

        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);

        List<MessageDetail> messageDetailList = namedParameterJdbcTemplate.query(sql, map, new MessageDetailRowMapper());

        return messageDetailList;
    }
}
