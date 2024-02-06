package com.Project.BackEnd.SubComment.Controller;

import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.Comment.Service.CommentService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import com.Project.BackEnd.SubComment.Entity.SubComment;
import com.Project.BackEnd.SubComment.Service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subcomment")
public class SubCommentController {
    private final CommentService commentService;
    private final SubCommentService subCommentService;
    private final MemberService memberService;

    // 대댓글 생성
    @PostMapping("/subcomments/comment/{id}")
    public ResponseEntity<String> createSubComment(@PathVariable("id") Long id, @RequestBody SubComment subComment, Principal principal) {
        try {
            Member member = this.memberService.getMember(principal.getName());
            Comment comment = this.commentService.findById(id);
            this.subCommentService.createSubComment(subComment.getContent(), member, comment);
            return ResponseEntity.ok("SubComment created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating subComment: " + e.getMessage());
        }
    }

    // 대댓글 수정
    @PatchMapping("/subcomments/comment/{id}")
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
    @DeleteMapping("/subcomments/comment/{id}")
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


