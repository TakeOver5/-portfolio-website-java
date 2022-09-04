package com.tapcus.portfoliowebsitejava.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.security.component.MyUser;
import com.tapcus.portfoliowebsitejava.util.JwtUtils;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 登入成功的 handler
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest res, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // 生成 jwt
        String jwt = jwtUtils.generateToken(authentication.getName());
        resp.setHeader(jwtUtils.getHeader(), jwt);

        // 取得登入資料
        Object object = authentication.getPrincipal();
        MyUser myuser = (MyUser) object;
        Member member = myuser.getMember();

        Map<String, Object> result = new HashMap<>();
        result.put("memberId", member.getMemberId());
        result.put("name", member.getName());
        result.put("email", member.getEmail());
        result.put("avatar", member.getAvatar());

        // 權限回顯
        Collection collection = authentication.getAuthorities();
        String authority = collection.iterator().next().toString();
        result.put("authority", authority);

        Result<Map<String, Object>> succResult = Result.success(result);
        succResult.setMessage("登入成功");

        // 寫入
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(succResult));
        out.flush();
        out.close();
    }
}
