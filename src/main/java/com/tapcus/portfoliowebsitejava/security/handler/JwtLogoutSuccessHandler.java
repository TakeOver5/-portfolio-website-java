package com.tapcus.portfoliowebsitejava.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapcus.portfoliowebsitejava.util.JwtUtils;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest res, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        // 如果身份驗證不為空，則調用退出處理器
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(res, resp, authentication);
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // 把回應頭清空
        resp.setHeader(jwtUtils.getHeader(), "");


        Result<String> succResult = Result.success("");

        // 寫入
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(succResult));
        out.flush();
        out.close();
    }
}
