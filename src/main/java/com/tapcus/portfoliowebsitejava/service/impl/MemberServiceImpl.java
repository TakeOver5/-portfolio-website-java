package com.tapcus.portfoliowebsitejava.service.impl;

import com.tapcus.portfoliowebsitejava.dao.ArticleDao;
import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.dto.ChangeNameRequest;
import com.tapcus.portfoliowebsitejava.dto.ChangePasswordRequest;
import com.tapcus.portfoliowebsitejava.dto.IndexArticleResponse;
import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Article;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.model.MemberInfo;
import com.tapcus.portfoliowebsitejava.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    ArticleDao articleDao;

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

    @Override
    public String setAuth(Integer memberId, Integer auth) {
        if(memberDao.getAuth(memberId).equals("ROLE_admin"))
            return "沒有權限";

        String authName = auth==0 ? "ROLE_banner" : "ROLE_user";
        memberDao.setAuth(memberId, authName);
        return "修改成功";
    }

    @Override
    public MemberInfo getMemberInfo(Integer memberId) {
        Member member = memberDao.getMemberInfo(memberId);
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(member.getMemberId());
        memberInfo.setAvatar(member.getAvatar());
        memberInfo.setName(member.getName());
        memberInfo.setEmail(member.getEmail());
        memberInfo.setCreatedDate(member.getCreatedDate());

        List<Article> articleList = articleDao.getArticleByMemberId(memberId);
        List<IndexArticleResponse> iarList = new ArrayList<>();
        for(Article article : articleList) {
            IndexArticleResponse iar = new IndexArticleResponse();
            iar.setArticle_id(article.getArticleId());
            iar.setTitle(article.getTitle());
            iar.setIntroduction(article.getIntroduction());
            iar.setCover_path(article.getCoverPath());
            iarList.add(iar);
        }
        memberInfo.setArticles(iarList);

        return memberInfo;
    }

    @Override
    public String changePassword(ChangePasswordRequest cpr) {

        if(cpr.getOldPassword().equals(cpr.getNewPassword())) {
            return "輸入新舊密碼一致";
        }

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = (Member) object;

        PasswordEncoder pe = new BCryptPasswordEncoder();
        boolean matches = pe.matches(cpr.getOldPassword(), member.getPassword());

        if(!matches) {
            return "輸入的舊密碼與用戶密碼不匹配";
        }

        matches = pe.matches(cpr.getNewPassword(), member.getPassword());
        if(matches) {
            return "輸入的新密碼與用戶密碼一樣";
        }

        memberDao.changePasswordByMemberId(member.getMemberId(), pe.encode(cpr.getNewPassword()));

        return "修改成功";
    }

    @Override
    public String changeName(ChangeNameRequest cnr) {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = (Member) object;

        if(member == null) return "查無此帳號";

        String flag = memberDao.checkName(cnr.getName());
        if(flag != null) return "暱稱重覆";

        memberDao.setName(member.getMemberId(), cnr.getName());
        return "修改成功";
    }
}
