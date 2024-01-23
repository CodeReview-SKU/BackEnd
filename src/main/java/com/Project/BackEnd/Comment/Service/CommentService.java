package com.Project.BackEnd.Comment.Service;


import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.Comment.Repository.CommentRepository;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // Create 댓글 작성
    public void createComment(String content, Member member, Board board){
        Comment comment = new Comment();

        comment.setBoard(board);
        comment.setMember(member);
        comment.setCreateDate(LocalDateTime.now());
        comment.setContent(content);
        this.commentRepository.save(comment);
    }

    // Read
    // 게시물 id 기준 댓글 조회
    public List<Comment> findByBoardId(Long boardId){
        return commentRepository.findByBoardId(boardId);
    }
    // 멤버 id 기준 댓글 조회
    public List<Comment> findByMemberId(Long memberId){
        return commentRepository.findByMemberId(memberId);
    }
    // id 기준 조회
    public Comment findById(Long id){
        Optional<Comment> review = this.commentRepository.findById(id);
        if(review.isPresent()){
            return review.get();
        }
        else {
            throw new DataNotFoundException("Comment not found");
        }
    }

    // Update 댓글 수정
    public void modifyComment(String content, Comment comment){
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    // Delete 댓글 삭제
    public void deleteComment(Long commentId){
        this.commentRepository.deleteById(commentId);
    }
}
