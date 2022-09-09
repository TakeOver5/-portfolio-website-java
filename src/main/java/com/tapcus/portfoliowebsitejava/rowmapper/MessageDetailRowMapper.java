package com.tapcus.portfoliowebsitejava.rowmapper;

import com.tapcus.portfoliowebsitejava.model.ArticleDetail;
import com.tapcus.portfoliowebsitejava.model.MessageDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDetailRowMapper implements RowMapper<MessageDetail> {
    @Override
    public MessageDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

        MessageDetail mg = new MessageDetail();
        mg.setMessageId(rs.getInt("message_id"));
        mg.setContent(rs.getString("content"));
        mg.setCreatedDate(rs.getTimestamp("created_date"));
        mg.setAvatar(rs.getBytes("avatar"));
        mg.setName(rs.getString("name"));
        return mg;
    }
}
