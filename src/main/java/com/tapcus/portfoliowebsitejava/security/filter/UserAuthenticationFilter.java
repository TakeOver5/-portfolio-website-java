package com.tapcus.portfoliowebsitejava.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// 使用 request body 方式登入
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = this.getBodyParams(request).get(super.SPRING_SECURITY_FORM_PASSWORD_KEY);
        if(!StringUtils.isEmpty(password)) {
            return password;
        }
        return super.obtainPassword(request);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username = this.getBodyParams(request).get(super.SPRING_SECURITY_FORM_USERNAME_KEY);
        if(!StringUtils.isEmpty(username)) {
            return username;
        }
        return super.obtainUsername(request);
    }
    private Map<String, String> getBodyParams(HttpServletRequest request) {
        Map<String, String> bodyParams = threadLocal.get();
        if(bodyParams == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                bodyParams = objectMapper.readValue(is, Map.class);
            } catch (IOException e) {

            }
            if(bodyParams == null) bodyParams = new HashMap<>();
            threadLocal.set(bodyParams);
        }
        return bodyParams;
    }
}
