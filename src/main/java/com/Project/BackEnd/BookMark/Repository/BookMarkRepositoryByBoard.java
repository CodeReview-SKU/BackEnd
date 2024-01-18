package com.Project.BackEnd.BookMark.Repository;


import com.Project.BackEnd.Board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookMarkRepository extends JpaRepository<Board, Long> {
    Board findByTitle(String title);

}
