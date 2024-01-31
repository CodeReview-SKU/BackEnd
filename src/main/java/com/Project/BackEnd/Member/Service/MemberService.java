package com.Project.BackEnd.Member.Service;

import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Jwt.DTO.JwtDTO;
import com.Project.BackEnd.Member.DTO.MemberDTO;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.Project.BackEnd.Member.Entity.Member.accountProvider;
import com.Project.BackEnd.Member.Entity.Member.role;

import javax.management.RuntimeErrorException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void create(MemberDTO memberDTO) {
        Member member = Member.builder()
                .name(memberDTO.getName())
                .accountProvider(memberDTO.getAccountProvider())
                .role(role.USER)
                .build();


        this.memberRepository.save(member);
    }




    public Member getMember(String name) {
        Optional<Member> member = this.memberRepository.findByName(name);
        if (member.isPresent()) {
            return member.get();
        }
        else {
            throw new DataNotFoundException("Data Not Found");
        }
    }

    public void update(Member member, String name) {
        member.setName(name);
        this.memberRepository.save(member);
    }

    public void delete(Member member) {
        this.memberRepository.delete(member);
    }



}
