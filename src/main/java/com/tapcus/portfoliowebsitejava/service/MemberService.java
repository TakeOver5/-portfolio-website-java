package com.tapcus.portfoliowebsitejava.service;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    Integer register(MemberRegisterRequest memberRegisterRequest);

    List<Member> getMembers(Integer limit, Integer offset);

    Integer countMember();

    byte[] updateAvatar(Integer memberId, MultipartFile file) throws IOException;
}
