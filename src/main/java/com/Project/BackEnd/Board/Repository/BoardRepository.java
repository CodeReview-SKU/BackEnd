package com.Project.BackEnd.Board.Repository;

import com.Project.BackEnd.Board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.Project.BackEnd.Board.Entity.Board.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title); //제목으로 검색

    List<Board> findByTag(tag tag);

    Page<Board> findByCategory(Pageable pageable, category category);

    Page<Board> findByTag(Pageable pageable, tag tag);

    Page<Board> findAll(Pageable pageable); //모든 게시물 검색 (페이지 로드)
}
