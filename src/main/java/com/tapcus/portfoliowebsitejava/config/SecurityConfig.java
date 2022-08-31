package com.tapcus.portfoliowebsitejava.config;

import com.tapcus.portfoliowebsitejava.security.filter.JwtAuthenticationFilter;
import com.tapcus.portfoliowebsitejava.security.filter.UserAuthenticationFilter;
import com.tapcus.portfoliowebsitejava.security.handler.JwtAccessDeniedHandler;
import com.tapcus.portfoliowebsitejava.security.handler.JwtAuthenticationEntryPoint;
import com.tapcus.portfoliowebsitejava.security.handler.JwtLogoutSuccessHandler;
import com.tapcus.portfoliowebsitejava.security.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailService;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    // 添加 jwt 過濾器
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }

    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定義用戶驗證和加密方式
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 可跨域
        http.cors().and().csrf().disable();
        // 禁止 session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin()
                .and()
                .authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/welcome").hasRole("user")
                .antMatchers("/members").hasRole("admin")
                .antMatchers(URL_WHITELIST).permitAll()
                .and()
                // 登出配置
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler);
        //.logoutSuccessUrl("/login").permitAll()

        // 異常處理
        http.exceptionHandling()
                // 入口
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 權限不足
                .accessDeniedHandler(jwtAccessDeniedHandler);

        // 配置
        http.addFilterAt(UserAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class)
                // 增加 jwt 過濾器
                .addFilter(jwtAuthenticationFilter());
    }

    // 定義自定義登入過濾器
    private UserAuthenticationFilter UserAuthenticationFilterBean() throws  Exception {
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter();
        userAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        // 登入成功和失敗的 Handler
        userAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        userAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return userAuthenticationFilter;
    }

}
