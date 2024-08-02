package com.Project.BackEnd.Member.Service;

import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Jwt.Service.JwtService;
import com.Project.BackEnd.Member.DTO.loginInfo;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.Project.BackEnd.Member.Entity.Member.role;

import javax.management.RuntimeErrorException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void create(String userId, String password, String name, String email){
            Member member = new Member();
            member.setUserId(userId);
            member.setRole(role.USER);
            member.setPassword(encoder.encode(password));
            member.setName(name);
            member.setEmail(email);
            member.setRefreshToken(jwtService.createRefreshToken());
        this.memberRepository.save(member);
    }

    public loginInfo login(String userId, String password) throws Exception {
        log.info("로그인 시도 - 사용자 ID: {}", userId);

        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("이메일 및 패스워드 확인");
        }

        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(member.getRole().name()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password, authorityList);
        log.info(authenticationToken.toString());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            loginInfo res = new loginInfo();
            res.setName(String.valueOf(member.getId()));
            res.setToken(jwtService.createAccessToken(member.getName()));
            return res;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            log.error("인증 실패: {}", e.getMessage());
            throw new BadCredentialsException("이메일 및 패스워드를 확인하십시오.");
        }
    }


    public Member getMember(String name) {
        Optional<Member> member = this.memberRepository.findByName(name);
        return member.orElse(null);
    }

    public Member getMemberById(Long id) {
        Optional<Member> member = this.memberRepository.findById(id);
        if (member.isPresent()) {
            return member.get();
        }
        else {
            throw new DataNotFoundException("Data Not Found");
        }
    }

    public Member getMemberByUserId(String userId) {
        Optional<Member> member = this.memberRepository.findByUserId(userId);
        return member.orElse(null);
    }

    public void update(Member member, String name) {
        member.setName(name);
        this.memberRepository.save(member);
    }

    public void delete(Member member) {
        this.memberRepository.delete(member);
    }



}
