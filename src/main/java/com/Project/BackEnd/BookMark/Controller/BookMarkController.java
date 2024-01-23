package com.Project.BackEnd.BookMark.Controller;


import com.Project.BackEnd.Board.Service.BoardService;
import com.Project.BackEnd.BookMark.DTO.BookMarkDTO;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.BookMark.Service.BookMarkService;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
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


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<BookMark>> delete(@PathVariable long id) {

        this.bookMarkService.delete(this.bookMarkService.getBookMark(id));

        return ResponseEntity.ok(this.bookMarkService.getBookMarkList());
    }

    @ResponseBody
    @PostMapping("/new")
    public ResponseEntity<List<BookMark>> create(@RequestBody BookMarkDTO bookMarkDTO) {
        this.bookMarkService.create(bookMarkDTO.getMember(), bookMarkDTO.getBoard());

        return ResponseEntity.ok(this.bookMarkService.getBookMarkList());
    }



}
