package com.tapcus.portfoliowebsitejava.dao;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;

public interface MemberDao {

    // 創建會員
    Integer createMember(MemberRegisterRequest memberRegisterRequest);

    Member getMemberByEmail(String email);

}
