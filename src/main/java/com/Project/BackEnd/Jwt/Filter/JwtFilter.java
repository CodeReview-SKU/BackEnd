package com.Project.BackEnd.Jwt.Filter;


import com.Project.BackEnd.Jwt.Service.JwtService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private static final String NO_CHECK_URL = "/login"; // login은 해야하므로 여기로 들어오는 링크는 필터 x
    private static final String GET_URL = "/board";
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private GrantedAuthoritiesMapper grantedAuthoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.equals(NO_CHECK_URL) || requestURI.equals(GET_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        checkAccessTokenAuthorization(request, response, filterChain);
    }


    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse httpServletResponse, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent( member ->
                {
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    jwtService.sendAccessTokenAndRefreshToken(httpServletResponse,
                            jwtService.createAccessToken(member.getName()),reIssuedRefreshToken);
                });
    }
    public void checkAccessTokenAuthorization(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                              FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAcessTokenAuthrization 호출");
        jwtService.extractAccessToken(httpServletRequest)
                .filter(jwtService::isTokenValid).flatMap(accessToken -> jwtService.extractName(accessToken).flatMap(memberRepository::findByName)).ifPresent(this::saveAuthentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public void saveAuthentication(Member member) {
        log.info(member.getName());
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(member.getName())
                .roles(member.getRole().name())
                .password("")
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        grantedAuthoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    public String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        member.setRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }
}
