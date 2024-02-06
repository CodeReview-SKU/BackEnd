package com.Project.BackEnd.Member.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.BackEnd.Member.Entity.Member;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByRefreshToken(String refreshToken);
}
