package com.Project.BackEnd.Comment.Controller;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Service.BoardService;
import com.Project.BackEnd.Comment.DTO.CommentCreateDTO;
import com.Project.BackEnd.Comment.DTO.CommentDetailDTO;
import com.Project.BackEnd.Comment.DTO.CommentInfoDTO;
import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.Comment.Service.CommentService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "댓글 API", description = "게시판 컨트롤러에 대한 설명입니다.")
@RequestMapping("/api/comment")
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
    /*
    *** Member 객체로 List 조회
     */
    @GetMapping("/member/{id}")
    public ResponseEntity<List<CommentDetailDTO>> commentFindByMember(@PathVariable("id) Long id){
        try {
            List<CommentDetailDTO> comment = this.commentService.getCommentDetail(id);
            return ResponseEntity.ok(comment);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    /*
    *** Board 객체로 List 조회
     */
    @GetMapping("/board/{id}")
    public ResponseEntity<List<CommentInfoDTO>> commentFindByBoard(@PathVariable("id") Long id) {
        try {
            List<CommentInfoDTO> comment = this.commentService.getCommentInfo(id);
            return ResponseEntity.ok(comment);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

    /*
    *** 페이징 컨트롤러
     */
    @GetMapping("/list/{boardId}?page={page}")
    public ResponseEntity<Page<CommentInfoDTO>> getCommentList(@PathVariable("boardId") Long boardId, @PathVariable("page") Integer page){
        Page<CommentInfoDTO> commentList = this.commentService.getCommentList(boardId, page);
        return ResponseEntity.ok(commentList);
    }
}
