package com.Project.BackEnd.SubComment.Service;


import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.SubComment.DTO.SubCommentDetailDTO;
import com.Project.BackEnd.SubComment.DTO.SubCommentInfoDTO;
import com.Project.BackEnd.SubComment.Entity.SubComment;
import com.Project.BackEnd.SubComment.Repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubCommentService {

    private final SubCommentRepository subCommentRepository;

    // Create 대댓글 작성
    public void createSubComment(String content, Member member, Comment comment){
        SubComment subComment = new SubComment();

        subComment.setComment(comment);
        subComment.setMember(member);
        subComment.setContent(content);
        subComment.setCreateDate(LocalDateTime.now());
        this.subCommentRepository.save(subComment);
    }

    // Read
    // comment id 기준 대댓글 조회
    /*
    public List<SubComment> findByCommentId(long commentId){
        return subCommentRepository.findByCommentId(commentId);
    }
     */

    public SubComment findById(long id){
        Optional<SubComment> subComment = this.subCommentRepository.findById(id);
        if (subComment.isPresent()){
            return subComment.get();
        }else {
            throw new DataNotFoundException("SubComment not found");
        }
    }

    /*
    *** SubCommentInfoDTO로 comment id 기준으로 대댓글 조회
     */
    public List<SubCommentInfoDTO> getSubCommentInfo(long id) {
        return this.subCommentRepository.findByCommentId(id);
    }
    /*
    *** SubCommentDetailDTO로 member id 기준으로 대댓글 조회
     */
    public List<SubCommentDetailDTO> getSubCommentDetail(long id) {
        return this.subCommentRepository.findByMemberId(id);
    }

    // Update 대댓글 수정
    public void modifySubComment(String content, SubComment subComment){
        subComment.setModifyDate(LocalDateTime.now());
        subComment.setContent(content);
        this.subCommentRepository.save(subComment);
    }

    // Delete 대댓글 삭제
    public void deleteSubComment(long subCommentId){
        this.subCommentRepository.deleteById(subCommentId);
    }

    // 좋아요 증가
    public void increaseLike(SubComment subComment){
        int likeCnt = readLikeCnt(subComment);
        likeCnt += 1;
        subComment.setLike_cnt(likeCnt);
        this.subCommentRepository.save(subComment);
    }
    // 좋아요 감소 -도 가능
    public void decreaseLike(SubComment subComment){
        int likeCnt = readLikeCnt(subComment);
        likeCnt -=1;
        subComment.setLike_cnt(likeCnt);
        this.subCommentRepository.save(subComment);
    }
    // 좋아요 수 조회  -> 생각해봐야함 수정 필요 (효율적으로)
    public int readLikeCnt(SubComment subComment){
        return subComment.getLike_cnt();
    }
}
