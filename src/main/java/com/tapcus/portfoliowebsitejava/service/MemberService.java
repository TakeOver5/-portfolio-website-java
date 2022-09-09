package com.tapcus.portfoliowebsitejava.service;

import com.tapcus.portfoliowebsitejava.dto.ChangePasswordRequest;
import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.model.MemberInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    Integer register(MemberRegisterRequest memberRegisterRequest);

    List<Member> getMembers(Integer limit, Integer offset);

    Integer countMember();

    byte[] updateAvatar(Integer memberId, MultipartFile file) throws IOException;

    String setAuth(Integer memberId, Integer auth);

    MemberInfo getMemberInfo(Integer memberId);

    String changePassword(ChangePasswordRequest cpr);
}
