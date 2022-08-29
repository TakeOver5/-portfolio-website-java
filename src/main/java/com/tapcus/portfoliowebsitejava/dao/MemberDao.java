package com.tapcus.portfoliowebsitejava.dao;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;

public interface MemberDao {

    // 創建會員
    Integer createUser(MemberRegisterRequest memberRegisterRequest);

}
