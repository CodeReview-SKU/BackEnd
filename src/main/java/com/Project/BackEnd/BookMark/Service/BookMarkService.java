package com.Project.BackEnd.BookMark.Service;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Repository.BoardRepository;
import com.Project.BackEnd.BookMark.DTO.BookMarkDetailDTO;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.BookMark.Repository.BookMarkRepository;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    public List<BookMark> getBookMarkByMember(String id) {
        Optional<Member> member = this.memberRepository.findById(Long.parseLong(id));
        List<BookMark> bookMark = this.bookMarkRepository.findByMember(member.get());

        if (!bookMark.isEmpty()) {
            return bookMark;
        }
        else {
            return null;
        }
    }

    public List<BookMark> getBookMarkList() {
        List<BookMark> list = this.bookMarkRepository.findAll();
        if (!list.isEmpty()){
            return list;
        }
        else {
            throw new DataNotFoundException("NO DATA");
        }
    }
    public int getBookMarkCount(Board board) {
        List<BookMark> list = this.bookMarkRepository.findByBoard(board);
        return list.size();
    }


    public void create(Member member, Board board) {
        BookMark bookMark = new BookMark();
        bookMark.setMember(member);
        bookMark.setBoard(board);
        this.bookMarkRepository.save(bookMark);
    }

    public BookMark getBookMark(long id) {
        Optional<BookMark> bookMark = this.bookMarkRepository.findById(id);
        if (bookMark.isPresent()) {
            return bookMark.get();
        }
        else{
            throw new DataNotFoundException("Data Not Found");
        }
    }

    public BookMark getBookMarkByMemberAndBoard(long memberId, long boardId) {
        Optional<BookMark> bookMark = this.bookMarkRepository.findByMemberIdAndBoardId(memberId, boardId);
        if (bookMark.isPresent()) {
            return bookMark.get();
        }
        else {
            throw new DataNotFoundException("Data Not Found");
        }
    }

    public void update(BookMark bookMark, Board board) {
        bookMark.setBoard(board);
        this.bookMarkRepository.save(bookMark);
    }

    public void delete(BookMark bookMark) {
        this.bookMarkRepository.delete(bookMark);
    }

    /*
    *** member id 기준 북마크 조회 -> 프로필 조회 목적
     */
    public List<BookMarkDetailDTO> getBookMarkDetail(long id) {
        return this.bookMarkRepository.findByMemberId(id);
    }


}
