package com.Project.BackEnd.BookMark.Controller;


import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Service.BoardService;
import com.Project.BackEnd.BookMark.DTO.BookMarkDTO;
import com.Project.BackEnd.BookMark.DTO.BookMarkDetailDTO;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.BookMark.Service.BookMarkService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;
    private final MemberService memberService;
    private final BoardService boardService;


    @GetMapping("/list/{id}")
    public ResponseEntity<List<BookMark>> bookMarkDetail(@PathVariable String id) {
        List<BookMark> bookMarks = this.bookMarkService.getBookMarkByMember(id);
        return ResponseEntity.ok(bookMarks);
    }

    @GetMapping("/get/{boardId}/{memberId}")
    public ResponseEntity<Boolean> getBookMark(@PathVariable String boardId, @PathVariable String memberId) {
        try {
            Long boardIdLong = Long.parseLong(boardId);
            Long memberIdLong = Long.parseLong(memberId);

            if (bookMarkService.getBookMarkByMemberAndBoard(memberIdLong, boardIdLong) == null) {
                return ResponseEntity.ok(false);
            }

            return ResponseEntity.ok(true);
        } catch (NumberFormatException e) {
            log.error("Invalid ID format for boardId: {} or memberId: {}", boardId, memberId, e);
            return ResponseEntity.badRequest().body(false);
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching bookmark for boardId: {} and memberId: {}", boardId, memberId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }



    @DeleteMapping("/delete/{boardId}/{memberId}")
    public ResponseEntity<String> delete(@PathVariable long boardId, @PathVariable long memberId) {
        try {
            this.bookMarkService.delete(bookMarkService.getBookMarkByMemberAndBoard(memberId, boardId));
            this.boardService.downLikeCount(boardId);
            return ResponseEntity.ok("bookmark is deleted.");
        }
        catch (Exception e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(500));
        }

    }

    @PostMapping("/create")
    public ResponseEntity<List<BookMark>> create(@RequestBody BookMarkDTO bookMarkDTO) {

        Member member = memberService.getMemberById(Long.parseLong(bookMarkDTO.getMember()));
        Board board = boardService.getBoard(Long.parseLong(bookMarkDTO.getBoard()));
        this.bookMarkService.create(member, board);
        this.boardService.upLikeCount(Long.parseLong(bookMarkDTO.getBoard()));

        return ResponseEntity.ok(this.bookMarkService.getBookMarkList());
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> count(@PathVariable long id) {
        return ResponseEntity
                .ok(this.bookMarkService
                        .getBookMarkCount(this.boardService.getBoard(id)));
    }

    /*
    *** member id 기준 bookMark 조회 -> 프로필 조회 목적
     */
    @GetMapping("/member/{id}")
    public ResponseEntity<List<BookMarkDetailDTO>> getBookMarkDetail(@PathVariable("id") long id) {
        try {
            List<BookMarkDetailDTO> bookMark = this.bookMarkService.getBookMarkDetail(id);
            return ResponseEntity.ok(bookMark);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
