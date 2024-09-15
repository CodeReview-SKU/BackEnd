package com.Project.BackEnd.Member.Repository;


import com.Project.BackEnd.Member.DTO.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Project.BackEnd.Member.Entity.Member;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByRefreshToken(String refreshToken);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByEmail(String email);

    @Query("select new com.Project.BackEnd.Member.DTO.MemberDTO(m.id, m.name, m.password ,m.email, m.userId)" +
            "from Member as m " +
            "where m.id = :id")
    MemberDTO findByUserId2(long id);
}
