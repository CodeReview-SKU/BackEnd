package com.Project.BackEnd.OAuth.Handler;

import com.Project.BackEnd.Jwt.Service.JwtService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import com.Project.BackEnd.OAuth.CustomOAuth2User;
import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/*
설명 : OAuth2 로그인 성공 핸들러 메소드 정의
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService; // JWT 토큰 관련 메소드를 이용하기 위해 호출
    private final MemberRepository memberRepository; // 멤버를 저장해야 하는 경우도 있으므로 레포지토리 호출
    private final String client = "http://localhost:5173"; // 리다이렉트 보낼 클라이언트

    // OAuth2 로그인 성공 시, 토큰 과 사용자 정보를 클라이언트로 쿼리스트링 통해 보낸다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공");
        log.info("로그인한 사용자 : {}", authentication.getName());
        log.info(SecurityContextHolder.getContext().toString());
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if (oAuth2User.getRole() == Member.role.USER) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getName());
                long name = memberRepository.findByName(jwtService.extractName(accessToken).get()).get().getId();
                String redirectUrl = client + "/login/redirect?name=" + name + "&accessToken=" + accessToken;
                response.sendRedirect(redirectUrl);
                loginSuccess(response, oAuth2User);
            }
            else {
                loginSuccess(response, oAuth2User);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getName());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer" + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer" + refreshToken);

        jwtService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getName(), refreshToken);
    }
}
