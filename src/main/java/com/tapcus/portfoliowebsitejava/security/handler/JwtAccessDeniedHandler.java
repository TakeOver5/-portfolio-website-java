package com.tapcus.portfoliowebsitejava.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Spring Security 判斷是身分錯誤的時候，會經過的 handler
// 這邊能定義要回復的 json 訊息，並回傳 status code = 403
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        // 權限不足
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        Result<String> failResult = Result.error("權限不足");
        failResult.setCode(401);
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(failResult));
        out.flush();
        out.close();
    }
}
