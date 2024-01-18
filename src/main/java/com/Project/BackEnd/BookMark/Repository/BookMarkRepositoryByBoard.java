package com.Project.BackEnd.BookMark.Repository;


import com.Project.BackEnd.Board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookMarkRepositoryByBoard extends JpaRepository<Board, Long> {
    Optional<Board> findByTitle(String title);
}


