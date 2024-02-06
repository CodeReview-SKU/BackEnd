package com.Project.BackEnd.Login.Handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

//    @Override
//    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception)
//    throws IOException {
//        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.setContentType("text/plain;charset=UTF-8");
//
//    } 이게 꼭 필요할까?
}
