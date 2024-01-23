package com.Project.BackEnd.BookMark.Service;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.BookMark.Repository.BookMarkRepository;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
/*
    public BookMark getBookMarkRepositoryByBoard(String title) {
        Optional<BookMark> bookMark = this.bookMarkRepository.findByMember();
        if (bookMark.isPresent()) {
            return bookMark.get();
        }
        else {
            throw new DataNotFoundException("Data not Found");
        }
    }

    public BookMark getBookMarkRepositoryByMember(String name) {
        Optional<BookMark> board = this.bookMarkRepository.findByName(name);
        if (board.isPresent()) {
            return board.get();
        }
        else {
            throw new DataNotFoundException("Data not Found");
        }
    }

    public void create(Member member, Board board) {
        BookMark bookMark = new BookMark();
        bookMark.setMember(member);
        bookMark.setBoard(board);
        this.bookMarkRepository.save(bookMark);
    }
    */

}
