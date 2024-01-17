package com.Project.BackEnd.Board.Repository;

import com.Project.BackEnd.Board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByTitle(String title);
    Board findByTitleAndContent(String title, String content);
    Board findByTitleLike(String title);

    Page<Board> findAll(Pageable pageable);
}
