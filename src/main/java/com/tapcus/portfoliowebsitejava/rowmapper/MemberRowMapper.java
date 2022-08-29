package com.tapcus.portfoliowebsitejava.rowmapper;

import com.tapcus.portfoliowebsitejava.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {

        Member member = new Member();
        member.setMemberId(rs.getInt("member_id"));
        member.setAvatar(rs.getBytes("avatar"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        member.setCreatedDate(rs.getTimestamp("created_date"));
        member.setAuth(rs.getString("auth"));

        return member;
    }
}
