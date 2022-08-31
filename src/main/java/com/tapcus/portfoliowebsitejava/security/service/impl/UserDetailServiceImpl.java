package com.tapcus.portfoliowebsitejava.security.service.impl;

import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.security.component.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// 透過 username 取得資料庫帳號密碼權限，返回值，Authentication 比對
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberDao.getMemberByEmail(username);

        if(member == null) {
            throw new UsernameNotFoundException("帳號或密碼錯誤！");
        }

        String password = member.getPassword();
        String authority = member.getAuth();
        int memberId = member.getMemberId();

        return new MyUser(username,
                          password,
                          AuthorityUtils.commaSeparatedStringToAuthorityList(authority),
                          memberId);
    }
}
