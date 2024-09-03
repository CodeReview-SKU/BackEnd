package com.Project.BackEnd.BookMark.Repository;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.BookMark.DTO.BookMarkDetailDTO;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    List<BookMark> findByMember(Member member);
    List<BookMark> findByBoard(Board board);
    Optional<BookMark> findByMemberIdAndBoardId(long memberId, long boardId);
    @Query("select new com.Project.BackEnd.BookMark.DTO.BookMarkDetailDTO(bm.id, bm.board.title) " +
            "from BookMark as bm " +
            "where bm.member.id = :id ")
    List<BookMarkDetailDTO> findByMemberId(long id);
}


