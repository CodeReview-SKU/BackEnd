package com.Project.BackEnd.Login.Service;


import com.Project.BackEnd.Member.DTO.MemberDTO;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저"));


        return org.springframework.security.core.userdetails.User.builder()
                .username(userId)
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();

    }


}
