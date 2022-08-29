package com.tapcus.portfoliowebsitejava.dao.impl;

import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.rowmapper.MemberRowMapper;
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
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Integer createMember(MemberRegisterRequest memberRegisterRequest) {
        String sql = "INSERT INTO member(name, email, password) " +
                     "VALUES (:name, :email, :password)";

        Map<String, Object> map = new HashMap<>();

        map.put("name", memberRegisterRequest.getName());
        map.put("email", memberRegisterRequest.getEmail());
        map.put("password", memberRegisterRequest.getPassword());

        // 取得自增鍵
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int memberId= keyHolder.getKey().intValue();

        return memberId;
    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "SELECT member_id, avatar, name, email, password, created_date, auth " +
                "FROM member WHERE email = :email";
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<Member> memberList = namedParameterJdbcTemplate.query(sql, map, new MemberRowMapper());

        if (memberList.size() > 0)
            return memberList.get(0);
        return null;
    }
}