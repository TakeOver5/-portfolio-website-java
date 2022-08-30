package com.tapcus.portfoliowebsitejava.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        // 401 未認證
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        Result<String> failResult = Result.error("認證失敗，請先登入", authException.getMessage());
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(failResult));
        out.flush();
        out.close();
    }
}
