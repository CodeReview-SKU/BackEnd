package com.Project.BackEnd.Member.Service;

import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.Project.BackEnd.Member.Entity.Member.accountProvider;
import com.Project.BackEnd.Member.Entity.Member.role;

import javax.management.RuntimeErrorException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member create(String name, accountProvider accountProvider, role role) {
        Member member = new Member();
        member.setName(name);
        member.setAccountProvider(accountProvider);
        member.setRole(role);

        return member;
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

}
