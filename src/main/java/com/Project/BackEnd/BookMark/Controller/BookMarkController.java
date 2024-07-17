package com.Project.BackEnd.BookMark.Controller;


import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Service.BoardService;
import com.Project.BackEnd.BookMark.DTO.BookMarkDTO;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.BookMark.Service.BookMarkService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book_mark")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;
    private final MemberService memberService;
    private final BoardService boardService;


    @GetMapping("/list/{name}")
    public ResponseEntity<List<BookMark>> bookMarkDetail(@PathVariable String name) {
        List<BookMark> bookMarks = this.bookMarkService.getBookMarkByMember(name);
        return ResponseEntity.ok(bookMarks);
    }


    @DeleteMapping("/delete/{boardId}/{memberId}")
    public ResponseEntity<String> delete(@PathVariable long boardId, @PathVariable long memberId) {

        this.bookMarkService.delete(bookMarkService.getBookMarkByMemberAndBoard(memberId, boardId));

        return ResponseEntity.ok("bookmark is deleted.");
    }

    @ResponseBody
    @PostMapping("/new")
    public ResponseEntity<List<BookMark>> create(@RequestBody BookMarkDTO bookMarkDTO) {

        Member member = memberService.getMemberById(Long.parseLong(bookMarkDTO.getMember()));
        Board board = boardService.getBoard(Long.parseLong(bookMarkDTO.getBoard()));
        this.bookMarkService.create(member, board);

        return ResponseEntity.ok(this.bookMarkService.getBookMarkList());
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> count(@PathVariable long id) {
        return ResponseEntity
                .ok(this.bookMarkService
                        .getBookMarkCount(this.boardService.getBoard(id)));
    }


}
