package com.tapcus.portfoliowebsitejava.dao;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;

import java.util.List;

public interface MemberDao {

    // 創建會員
    Integer createMember(MemberRegisterRequest memberRegisterRequest);

    Member getMemberByEmail(String email);

    List<Member> getMembers(Integer limit, Integer offset);

    Integer countProduct();

    byte[] updateAvatar(Integer memberId, byte[] bytes);
}
