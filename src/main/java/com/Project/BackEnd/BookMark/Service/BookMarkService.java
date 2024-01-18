package com.Project.BackEnd.BookMark.Service;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.BookMark.Repository.BookMarkRepositoryByBoard;
import com.Project.BackEnd.BookMark.Repository.BookMarkRepositoryByMember;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookMarkService {
    private final BookMarkRepositoryByMember bookMarkRepositoryByMember;
    private final BookMarkRepositoryByBoard bookMarkRepositoryByBoard;

    public Board getBookMarkRepositoryByBoard(String title) {
        Optional<Board> board = this.bookMarkRepositoryByBoard.findByTitle(title);
        if (board.isPresent()) {
            return board.get();
        }
        else {
            throw new DataNotFoundException("Data not Found");
        }
    }

    public Board getBookMarkRepositoryByMember(String name) {
        Optional<Board> board = this.bookMarkRepositoryByMember.findByName(name);
        if (board.isPresent()) {
            return board.get();
        }
        else {
            throw new DataNotFoundException("Data not Found");
        }
    }

    public BookMark create(Member member, Board board) {
        BookMark bookMark = new BookMark();
        bookMark.setMember(member);
        bookMark.setBoard(board);

        return bookMark;
    }
}
