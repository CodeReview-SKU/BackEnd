package com.Project.BackEnd.Board.Service;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Repository.BoardRepository;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

    // 멤버, 제목, 내용, 카테고리, 북마크(0), 태그
    public void create(Member member, String title, String content, String sourceCode, // Create
                       category category, tag tag) {
        Board board = new Board();
        board.setMember(member);
        board.setTitle(title);
        board.setContent(content);
        board.setSource_code(sourceCode);
        board.setCategory(category);
        board.setBookmark_cnt(0); // 생성 당시의 북마크된 개수는 무조건 0.
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

    public Board getBoard(Long id) {  // Read
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("Data Not Found");
        }

    }

    public List<Board> getBoardListByTitle(String title) {
        List<Board> board = this.boardRepository.findByTitle(title);
        if (!board.isEmpty()) {
            return board;
        } else {
            throw new DataNotFoundException("Data not found");
        }
    }

    public List<Board> getBoardListByTitleLike(String title) { // 게시물에 포함된 문자열에 대해서 리스트 반환
        List<Board> board = this.boardRepository.findAll(Sort.by(Sort.Direction.DESC, "write_date"));
        board.removeIf(t -> !t.getTitle().contains(title));
        if (!board.isEmpty()) {
            return board;
        } else {
            throw new DataNotFoundException("Data not found");
        }
    }

    public List<Board> getBoardList() {  // 페이지 8개의 게시물 리스트로 보여주는 부분
        List<Board> list = boardRepository.findAll();
        list.sort(Comparator.comparing(Board::getWrite_date));
        // 작성 일시를 기준으로 내림차순
        return list;
    }

    public List<Board> getBoardListByMember(long memberId) {
        return boardRepository.findByMemberId(memberId);
    }

    public List<Board> getBoardListByCategory(category category) { // 카테고리 별로 탐색
        List<Board> list = boardRepository.findAll();
        list.removeIf(l -> !l.getCategory().equals(category));
        list.sort(Comparator.comparing(Board::getWrite_date));
        return list;
    }

    public List<Board> getBoardListByTag(tag tag) { // 태그 별로 탐색
        List<Board> list = boardRepository.findAll();
        list.removeIf(l -> !l.getTag().equals(tag));
        list.sort(Comparator.comparing(Board::getWrite_date));
        return list;
    }

    @Transactional
    public void upLikeCount(long boardId) {
        try {
            this.boardRepository.incrementLikeCount(boardId);
        }
        catch (Exception e) {
            throw new DataNotFoundException("Data Not Found " + e.getMessage());
        }
    }

    @Transactional
    public void downLikeCount(long boardId) {
        try {
            this.boardRepository.decrementLikeCount(boardId);
        }
        catch (Exception e) {
            throw new DataNotFoundException("Data Not Found " + e.getMessage());
        }
    }
}
