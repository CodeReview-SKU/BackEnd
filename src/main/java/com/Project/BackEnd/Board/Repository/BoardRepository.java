package com.Project.BackEnd.Board.Repository;

import com.Project.BackEnd.Board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByTitle(String title); //제목으로 검색
    Board findByTitleAndContent(String title, String content); //제목과 내용으로 검색
    Board findByTitleLike(String title); //제목에 포함된 단어로 검색

    Page<Board> findAll(Pageable pageable); //모든 게시물 검색 (페이지 로드)
}
