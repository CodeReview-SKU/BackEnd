package com.Project.BackEnd.Comment.Repository;

import com.Project.BackEnd.Comment.DTO.CommentDetailDTO;
import com.Project.BackEnd.Comment.DTO.CommentInfoDTO;
import com.Project.BackEnd.Comment.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Long boardId);

    List<Comment> findByMemberId(Long memberId);
    @Query("select new com.Project.BackEnd.Comment.DTO.CommentDetailDTO(c.id, c.content, c.boardTitle, c.createDate, c.modifyDate)" +
            "from Comment as c " +
            "where c.member.id = :id")
    List<CommentDetailDTO> findDetailByMember(@Param("id") Long id);

    @Query("select new com.Project.BackEnd.Comment.DTO.CommentInfoDTO(c.id, c.content, c.member.name, c.createDate, c.modifyDate)" +
            "from Comment as c " +
            "where c.board.id = :id")
    List<CommentInfoDTO> findInfoByBoard(@Param("id") Long id);

    @Query("select new com.Project.BackEnd.Comment.DTO.CommentInfoDTO(c.id, c.content, c.member.name, c.createDate, c.modifyDate) " +
            "from Comment as c " +
            "where c.board.id = :id")
    Page<CommentInfoDTO> findCommentListByBoard(@Param("id") Long id, Pageable pageable);
}
