package com.tapcus.portfoliowebsitejava.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 登入失敗的 Handler
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest res, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Result<String> failResult = Result.error("登入失敗");
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(failResult));
        out.flush();
        out.close();
    }
}
