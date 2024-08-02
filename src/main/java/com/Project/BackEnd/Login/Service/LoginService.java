package com.Project.BackEnd.Login.Service;


import com.Project.BackEnd.Member.DTO.MemberDTO;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저"));


        return new org.springframework.security.core.userdetails.User(
                member.getUserId(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority(member.getRole().name()))
        );
    }


}
