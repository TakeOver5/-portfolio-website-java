package com.tapcus.portfoliowebsitejava.service.impl;

import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public Integer register(MemberRegisterRequest memberRegisterRequest) {
        // 檢查 email 重覆問題，存在問題拋出異常

        // 密碼加密
        PasswordEncoder pe = new BCryptPasswordEncoder();
        memberRegisterRequest.setPassword(pe.encode(memberRegisterRequest.getPassword()));

        return memberDao.createUser(memberRegisterRequest);
    }
}
