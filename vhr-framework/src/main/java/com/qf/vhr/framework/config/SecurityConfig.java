package com.qf.vhr.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.vhr.framework.entity.Hr;
import com.qf.vhr.framework.entity.RespBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    Hr hr = (Hr) auth.getPrincipal();
                    hr.setPassword(null);
                    RespBean respBean = RespBean.ok("登陆成功", hr);
                    out.write(new ObjectMapper().writeValueAsString(respBean));
                })
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    RespBean respBean = RespBean.error("登陆失败");
                    if (e instanceof BadCredentialsException){
                        respBean.setMsg("账户名或者密码输入错误，登陆失败");
                    }else if (e instanceof DisabledException){
                        respBean.setMsg("账户被禁用，登陆失败");
                    }
                    out.write(new ObjectMapper().writeValueAsString(respBean));
                })
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint((req, resp, e) -> {
                    //设置响应状态码为401
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            RespBean respBean = RespBean.error("尚未登录，请登录");
            out.write(new ObjectMapper().writeValueAsString(respBean));
        });
        return http.build();
    }
}
