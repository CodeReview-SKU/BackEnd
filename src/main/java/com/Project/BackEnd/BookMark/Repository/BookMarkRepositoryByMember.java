package com.Project.BackEnd.BookMark.Repository;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookMarkRepositoryByMember extends JpaRepository<Member, Long> {
    Optional<Board> findByName(String name);
}
