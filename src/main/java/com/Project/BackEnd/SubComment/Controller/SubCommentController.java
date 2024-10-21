package com.Project.BackEnd.SubComment.Controller;

import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.Comment.Service.CommentService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import com.Project.BackEnd.SubComment.DTO.SubCommentDTO;
import com.Project.BackEnd.SubComment.DTO.SubCommentDetailDTO;
import com.Project.BackEnd.SubComment.DTO.SubCommentInfoDTO;
import com.Project.BackEnd.SubComment.Entity.SubComment;
import com.Project.BackEnd.SubComment.Service.SubCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "대댓글 API", description = "게시판 컨트롤러에 대한 설명입니다.")
@RequestMapping("/api/subcomment")
public class SubCommentController {
    private final CommentService commentService;
    private final SubCommentService subCommentService;
    private final MemberService memberService;

    // 대댓글 생성
    @PostMapping("/create")
    public ResponseEntity<String> createSubComment(@RequestBody SubCommentDTO subCommentDTO) {
        try {
            Member member = this.memberService.getMemberById(Long.parseLong(subCommentDTO.getMember()));
            Comment comment = this.commentService.findById(Long.parseLong(subCommentDTO.getBoardId()));
            this.subCommentService.createSubComment(subCommentDTO.getContent(), member, comment);
            return ResponseEntity.ok("SubComment created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating subComment: " + e.getMessage());
        }
    }

    /*
    *** 대댓글 조회 -> comment id 기준으로 subComment List 반환
     */
    @GetMapping("/comment/{id}")
    public ResponseEntity<List<SubCommentInfoDTO>> getSubCommentInfo(@PathVariable("id") Long id){
        try{
            List<SubCommentInfoDTO> subComment = this.subCommentService.getSubCommentInfo(id);
            return ResponseEntity.ok(subComment);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    *** 프로필에서 대댓글 조회 목적 -> member id 기준으로 subComment List 반환
     */
    @GetMapping("/member/{id}")
    public ResponseEntity<List<SubCommentDetailDTO>> getSubCommentDetail(@PathVariable("id") Long id) {
        try{
            List<SubCommentDetailDTO> subComment = this.subCommentService.getSubCommentDetail(id);
            return ResponseEntity.ok(subComment);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 대댓글 수정
    @PatchMapping("/comment/{id}")
    public ResponseEntity<String> modifySubComment(@PathVariable("id") Long id, @RequestBody SubComment subComment,
                                                Principal principal) {
        try {
            // 존재하는 대댓글인지 확인
            subComment = this.subCommentService.findById(id);
            if (!subComment.getMember().getName().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
            }
            subCommentService.modifySubComment(subComment.getContent(), subComment);
            return ResponseEntity.ok("SubComment modified successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error modifying subComment: " + e.getMessage());
        }
    }

    // 대댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteSubComment(@PathVariable("id") Long id, Principal principal) {
        try {
            SubComment subComment = this.subCommentService.findById(id);
            if (!subComment.getMember().getName().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
            }
            this.subCommentService.deleteSubComment(id);
            return ResponseEntity.ok("SubComment deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting subComment: " + e.getMessage());
        }
    }

}


