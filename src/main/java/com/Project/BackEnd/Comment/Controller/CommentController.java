package com.Project.BackEnd.Comment.Controller;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Service.BoardService;
import com.Project.BackEnd.Comment.DTO.CommentCreateDTO;
import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.Comment.Service.CommentService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final BoardService boardService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<String> createComment(@RequestBody CommentCreateDTO comment){
//        log.info(comment.getContent());
//        log.info(comment.getBoard_id());
//        log.info(comment.getName());
        try {
            Member member = this.memberService.getMemberById(Long.parseLong(comment.getName()));
            Board board = this.boardService.getBoard(Long.parseLong(comment.getBoard_id()));
            this.commentService.createComment(comment.getContent(), member, board);
            return ResponseEntity.ok("Comment created successfully!");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error creating comment: " + e.getMessage());
        }
    }

    // Member id 기준 댓글 조회
    @GetMapping("/member/{id}")
    public ResponseEntity<List<Comment>> commentFindByMemberId(@PathVariable Long id){
        try{

            List<Comment> comments = this.commentService.findByMemberId(id);
            return ResponseEntity.ok(comments);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    // Board id 기준 댓글 조회
    @GetMapping("/board/{id}")
    public ResponseEntity<List<Comment>> commentFindByBoardId(@PathVariable Long id){
        try {
            List<Comment> comments = this.commentService.findByBoardId(id);
            return ResponseEntity.ok(comments);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    // Comment 수정
    @PatchMapping("/comments/{id}")
    public ResponseEntity<String> modifyComment(@PathVariable("id") Long id, @RequestBody Comment comment,
                                                Principal principal){
        try{
            comment = this.commentService.findById(id);
            if(!comment.getMember().getName().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
            }
            commentService.modifyComment(comment.getContent(),comment);
            return ResponseEntity.ok("Comment modified successfully!");
        }
        catch (Exception e){
            return  ResponseEntity.status(500).body("Error modifying comment: " + e.getMessage());
        }
    }

    // Comment 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id){
        try {
            this.commentService.deleteComment(id);
            return ResponseEntity.ok("Comment deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting comment: " + e.getMessage());
        }
    }


}
