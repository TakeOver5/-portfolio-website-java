package com.tapcus.portfoliowebsitejava.service;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;

import java.util.List;

public interface MemberService {

    Integer register(MemberRegisterRequest memberRegisterRequest);

    List<Member> getMembers(Integer limit, Integer offset);

    Integer countProduct();

}
