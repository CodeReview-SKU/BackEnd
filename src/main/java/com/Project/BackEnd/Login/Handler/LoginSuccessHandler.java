package com.Project.BackEnd.Login.Handler;


import com.Project.BackEnd.Jwt.Service.JwtService;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.net.http.HttpRequest;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String userName = exractUserName(authentication);
        String accessToken = jwtService.createAccessToken(userName);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessTokenAndRefreshToken(httpServletResponse, accessToken, refreshToken);
        memberRepository.findByName(userName)
                .ifPresent(member -> {
                    member.setRefreshToken(refreshToken);
                memberRepository.saveAndFlush(member);});

        log.info("로그인 성공 : {}", userName);
        log.info("발급된 access token : {}", accessToken);
    }

    private String exractUserName(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userDetails.getUsername();
    }
}
