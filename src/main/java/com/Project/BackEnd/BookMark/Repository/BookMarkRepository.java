package com.Project.BackEnd.BookMark.Repository;

import com.Project.BackEnd.BookMark.Entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByTitle(String title);
    Optional<BookMark> findByName(String name);
}


