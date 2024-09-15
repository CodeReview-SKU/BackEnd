package com.Project.BackEnd.OAuth.Handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/*
설명 : OAuth2 로그인 실패 핸들러
스프링 시큐리티 내에서 OAuth2 로그인을 실패 했을때 FailHandler 등록
 */
@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    // 실패 로직
    @Override
    public void onAuthenticationFailure (HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("로그인 실패 로그 확인 요망");
        log.info("로그인 실패 에러코드 {}", exception.getMessage());

    }
}
