package com.Project.BackEnd.Board.Service;

import com.Project.BackEnd.Board.Dto.BoardDetailDTO;
import com.Project.BackEnd.Board.Dto.BoardInfoDTO;
import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Repository.BoardRepository;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

import com.Project.BackEnd.Board.Entity.Board.category;
import com.Project.BackEnd.Board.Entity.Board.tag;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final int countsPerPage = 7;

    // 멤버, 제목, 내용, 카테고리, 북마크(0), 태그
    public void create(Member member, String title, String content, String sourceCode, // Create
                       category category, tag tag) {
        Board board = new Board();
        board.setMember(member);
        board.setTitle(title);
        board.setContent(content);
        board.setSource_code(sourceCode);
        board.setCategory(category);
        board.setLikeCount(0);
        board.setTag(tag);
        board.setWrite_date(LocalDateTime.now());

        this.boardRepository.save(board);
    }

    public void delete(Board board) { // Delete
        this.boardRepository.delete(board);
    }

    public void update(Board board, String title, String content, category category, tag tag) { //Update
        board.setTitle(title);
        board.setContent(content);
        board.setCategory(category);
        board.setTag(tag);
        this.boardRepository.save(board);
    }

    public void addImage(Board board, String image) { // 이미지 추가 메소드
        board.setImage(image);
        this.boardRepository.save(board);
    }

    public void addSourceCode(Board board, String source_code) { // 소스코드 삽입 메소드
        board.setSource_code(source_code);
        this.boardRepository.save(board);
    }

    public BoardDetailDTO getBoardDetail(Long id) {
        BoardDetailDTO board = this.boardRepository.findDetailByID(id);
        if(board == null) {
            throw new DataNotFoundException("Board Not Found");
        }
        else {
            return board;
        }
    }

    public Board getBoard(Long id) {  // Read
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("Data Not Found");
        }

    }


    public Page<BoardInfoDTO> getBoardList(int page) {  // 페이지 8개의 게시물 리스트로 보여주는 부분
        Pageable pageable = PageRequest.of(page, this.countsPerPage, Sort.Direction.DESC, "write_date");
        return this.boardRepository.findAllList(pageable);
    }

    public List<BoardInfoDTO> getBoardListByMember(long memberId) {
        return boardRepository.findByMemberId(memberId);
    }

    public void upLikeCount(long boardId) {
        try {
            this.boardRepository.incrementLikeCount(boardId);
        }
        catch (Exception e) {
            throw new DataNotFoundException("Data Not Found " + e.getMessage());
        }
    }

    public void downLikeCount(long boardId) {
        try {
            this.boardRepository.decrementLikeCount(boardId);
        }
        catch (Exception e) {
            throw new DataNotFoundException("Data Not Found " + e.getMessage());
        }
    }
}
