package com.Project.BackEnd.Comment.Service;


import com.Project.BackEnd.Board.Dto.BoardDetailDTO;
import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Comment.DTO.CommentDetailDTO;
import com.Project.BackEnd.Comment.DTO.CommentInfoDTO;
import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.Comment.Repository.CommentRepository;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final int countsPerPage = 19;

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
    public List<Comment> findByBoardId(long boardId){
        return commentRepository.findByBoardId(boardId);
    }
    // 멤버 id 기준 댓글 조회
    public List<Comment> findByMemberId(long memberId){
        return commentRepository.findByMemberId(memberId);
    }

    // id 기준 조회
    public Comment findById(long id){
        Optional<Comment> comment = this.commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        }
        else {
            throw new DataNotFoundException("Comment not found");
        }
    }
    /*
    *** member 객체로 Comment Detail 조회 / 프로필 조회 목적
     */
    public List<CommentDetailDTO> getCommentDetail(long id) {
        return this.commentRepository.findDetailByMember(id);
    }

    /*
    *** Board 객체로 Comment Info 조회 / 보드 댓글 조회 목적
     */
    public List<CommentInfoDTO> getCommentInfo(long id) {
        return this.commentRepository.findInfoByBoard(id);
    }

    // Update 댓글 수정
    public void modifyComment(String content, Comment comment){
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    // Delete 댓글 삭제
    public void deleteComment(long commentId){
        this.commentRepository.deleteById(commentId);
    }

    /*
    *** 페이징 기능
     */
    public Page<CommentInfoDTO> getCommentList(Long boardId, int page){ // 20개씩 페이징
        Pageable pageable = PageRequest.of(page, this.countsPerPage, Sort.Direction.DESC, "write_date");
        return this.commentRepository.findCommentListByBoard(boardId, pageable);
    }
}
