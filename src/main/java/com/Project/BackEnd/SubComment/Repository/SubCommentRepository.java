package com.Project.BackEnd.SubComment.Repository;

import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.SubComment.DTO.SubCommentDetailDTO;
import com.Project.BackEnd.SubComment.DTO.SubCommentInfoDTO;
import com.Project.BackEnd.SubComment.Entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {

    //List<SubComment> findByCommentId(Long commentId);

    @Query("select new com.Project.BackEnd.SubComment.DTO.SubCommentInfoDTO(sc.id, sc.content, sc.createDate, sc.modifyDate)" +
            "from SubComment as sc " +
            "where sc.comment.id = :id")
    List<SubCommentInfoDTO> findByCommentId(Long id);
    @Query("select new com.Project.BackEnd.SubComment.DTO.SubCommentDetailDTO(sc.id, b.title, sc.content, sc.createDate, sc.modifyDate)" +
            "from SubComment as sc " +
            "inner join Board as b on b.member.id = sc.member.id " +
            "where sc.member.id = :id ")
    List<SubCommentDetailDTO> findByMemberId(Long id);

}
