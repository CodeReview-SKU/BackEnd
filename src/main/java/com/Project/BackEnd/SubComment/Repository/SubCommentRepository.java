package com.Project.BackEnd.SubComment.Repository;

import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.SubComment.Entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {

    List<SubComment> findByCommentId(Long commentId);
}
