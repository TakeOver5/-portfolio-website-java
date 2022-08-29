package com.tapcus.portfoliowebsitejava.security.component;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUser extends User {

    private int memberId;

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, int memberId) {
        this(username, password, true, true, true, true, authorities, memberId);
    }

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, int memberId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.memberId = memberId;
    }

    public int getMemberId() {
        return this.memberId;
    }
}
