package com.Project.BackEnd.BookMark.Repository;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    List<BookMark> findByMember(Optional<Member> member);
    List<BookMark> findByBoard(Board board);
}


