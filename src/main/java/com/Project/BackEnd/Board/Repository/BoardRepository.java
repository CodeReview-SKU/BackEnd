package com.Project.BackEnd.Board.Repository;

import com.Project.BackEnd.Board.Dto.BoardDetailDTO;
import com.Project.BackEnd.Board.Dto.BoardInfoDTO;
import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Member.Entity.Member;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Project.BackEnd.Board.Entity.Board.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select new com.Project.BackEnd.Board.Dto.BoardInfoDTO(b.id, b.title, m.name, b.content, b.write_date, b.likeCount) from Board as b left join Member as m on b.member = m")
    Page<BoardInfoDTO> findAllList(Pageable pageable);

    @Query("select new com.Project.BackEnd.Board.Dto.BoardDetailDTO(b.id, b.title, m.name, b.content, b.source_code, b.image, b.category, b.tag, b.write_date, b.modified_date, b.likeCount) from Board as b left join fetch Member as m on b.member = m where b.id = :id")
    BoardDetailDTO findDetailByID(@Param("id") long id);

    @Query("select new com.Project.BackEnd.Board.Dto.BoardInfoDTO(b.id, b.title, m.name, b.content, b.write_date, b.likeCount) from Board as b left join fetch Member as m on b.member = m where m.id = :id")
    List<BoardInfoDTO> findByMemberId(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.likeCount = b.likeCount + 1 WHERE b.id = :boardId")
    void incrementLikeCount(Long boardId);

    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.likeCount = b.likeCount - 1 WHERE b.id = :boardId")
    void decrementLikeCount(Long boardId);
}
