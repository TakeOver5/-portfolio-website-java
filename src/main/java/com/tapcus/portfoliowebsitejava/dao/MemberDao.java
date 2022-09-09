package com.tapcus.portfoliowebsitejava.dao;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.model.MemberInfo;

import java.util.List;

public interface MemberDao {

    // 創建會員
    Integer createMember(MemberRegisterRequest memberRegisterRequest);

    Member getMemberByEmail(String email);

    List<Member> getMembers(Integer limit, Integer offset);

    Integer countMember();

    byte[] updateAvatar(Integer memberId, byte[] bytes);

    void setAuth(Integer memberId, String authName);

    String getAuth(Integer memberId);

    Member getMemberInfo(Integer memberId);

    void changePasswordByMemberId(Integer memberId, String newPassword);
}
