package com.tapcus.portfoliowebsitejava.service.impl;

import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import java.io.IOException;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    private final static Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Override
    public Integer register(MemberRegisterRequest memberRegisterRequest) {
        // 檢查 email 重覆問題，存在問題拋出異常
        Member member = memberDao.getMemberByEmail(memberRegisterRequest.getEmail());

        if(member != null) {
            // 表示註冊過了 400 參數有問題
            log.warn("該 email {} 己經被註冊了", memberRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email 已被註冊");
        }

        // 密碼加密
        PasswordEncoder pe = new BCryptPasswordEncoder();
        memberRegisterRequest.setPassword(pe.encode(memberRegisterRequest.getPassword()));

        return memberDao.createMember(memberRegisterRequest);
    }

    @Override
    public List<Member> getMembers(Integer limit, Integer offset) {

        return memberDao.getMembers(limit, offset);
    }

    @Override
    public Integer countMember() {
        return memberDao.countMember();
    }

    @Override
    public byte[] updateAvatar(Integer memberId, MultipartFile file) throws IOException {
        byte[] bytes = FileCopyUtils.copyToByteArray(file.getInputStream());
        return memberDao.updateAvatar(memberId, bytes);
    }
}
