package com.tapcus.portfoliowebsitejava.security.component;

import com.tapcus.portfoliowebsitejava.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUser extends User {

    private Member member;

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Member member) {
        this(username, password, true, true, true, true, authorities, member);
    }

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,Member member) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        setMember(member);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
