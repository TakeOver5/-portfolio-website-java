package com.tapcus.portfoliowebsitejava.security.filter;

import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MemberDao memberDao;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 獲得請求頭的 jwt
            String jwt = request.getHeader(jwtUtils.getHeader());

            // 首先判斷 jwt 是否為空
            // 匿名訪問
            if (jwt == null) {
                // 過濾鏈往下走
                // 去驗證白名單
                chain.doFilter(request, response);
                return;
            }

            // 解析 jwt，如果為空表示該 jwt 不合法
            Claims claim = jwtUtils.getClaimByToken(jwt);


            // jwt 已幫我們定義的例外
            if (claim == null)
                throw new JwtException("token 異常");

            if (jwtUtils.isTokenExpired(claim))
                throw new JwtException("token 已過期");


            // 通過驗證自然就能獲取用戶名稱
            String username = claim.getSubject();

            /* 這裡之後可以使用 jwt 把權限放表頭 */
            Member member = memberDao.getMemberByEmail(username);
            if(member == null) {
                throw new UsernameNotFoundException("帳號或密碼錯誤！");
            }
            String authority = member.getAuth();
            /* 結束 */


            // 獲取用戶的權限等訊息
            // 自動登入不用給密碼
            // 第一個給物件
            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(member, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authority));



            // 保存用戶是誰，是否被認證，具有哪些角色
            SecurityContextHolder.getContext().setAuthentication(token);

            // 過濾鏈往下走
            chain.doFilter(request, response);

        } catch (JwtException e) {
            e.printStackTrace();
            resolver.resolveException(request, response, null, e);
        }
    }
}
