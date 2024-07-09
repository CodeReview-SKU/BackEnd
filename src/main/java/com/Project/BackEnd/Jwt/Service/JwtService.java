package com.Project.BackEnd.Jwt.Service;


import com.Project.BackEnd.Member.Repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey; // 디코딩 키

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod; // 접근 토큰 만료 기한 정의

    @Value("${jwt.access.header}")
    private String accessHeader; // 접근 키

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod; // 재생성 토큰 만료 기한 정의

    @Value("${jwt.refresh.header}")
    private String refreshHeader; // 재생성 키


    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String NAME_CLAIM = "name";
    private static final String BEARER = "Bearer ";

    private final MemberRepository memberRepository;


    public String createAccessToken(String name) { // 접근 토큰 생성
        Date now = new Date();

        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT) // 접큰 토큰 제목 헤더 추가
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 만료 기한 지금 시각으로 부터
                .withClaim(NAME_CLAIM, name)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() { // 재생성 토큰 만듦
        Date now = new Date();

        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessToken(HttpServletResponse httpServletResponse, String accessToken) {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        httpServletResponse.setHeader(accessHeader, accessToken); // accessHeader : Authorization를 키로

        log.info("재발급 된 access Token : {}", accessToken);
    }

    public void sendAccessTokenAndRefreshToken(HttpServletResponse httpServletResponse, String accessToken,String refreshToken) {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setHeader(accessHeader, accessToken);
        httpServletResponse.setHeader(refreshHeader, refreshToken);

        log.info("access token, refresh token 생성 완료.");
    }


    public Optional<String> extractRefreshToken(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractName(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim("name")
                    .asString());
        } catch (Exception e) {
            log.info(accessToken);
            log.error("유효하지 않은 토큰2");
            return Optional.empty();
        }

    }


    public void updateRefreshToken(String name, String refreshToken) {
        memberRepository.findByName(name)
                .ifPresentOrElse(
                        member -> member.setRefreshToken(refreshToken),
                        () -> new Exception("일치하는 토큰이 없음.")
                );
    }


    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.info(token);
            log.error("유효하지 않은 토큰");
            return false;
        }
    }


}
