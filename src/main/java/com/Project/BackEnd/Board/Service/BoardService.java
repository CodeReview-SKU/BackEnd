package com.Project.BackEnd.Board.Service;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Repository.BoardRepository;
import com.Project.BackEnd.DataNotFoundException;
import com.Project.BackEnd.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.Project.BackEnd.Board.Entity.Board.category;
import com.Project.BackEnd.Board.Entity.Board.tag;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;


    public Board getBoard(Long id) {  // Read
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        }
        else {
            throw new DataNotFoundException("Data Not Found");
        }

    }

    public List<Board> getBoardByTitle(String title) {
        List<Board> board = this.boardRepository.findByTitle(title);
        if (!board.isEmpty()) {
            return board;
        }
        else {
            throw new DataNotFoundException("Data not found");
        }
    }

    public List<Board> getBoardByTitleLike(String title) { // 게시물에 포함된 문자열에 대해서 리스트 반환
        List<Board> board = this.boardRepository.findAll();
        board.removeIf(t -> !t.getTitle().contains(title));
        if (! board.isEmpty()) {
            return board;
        }
        else {
            throw new DataNotFoundException("Data not found");
        }
    }

    public List<Board> getBoardByTag(tag tag) {
        List<Board> boards = this.boardRepository.findByTag(tag);
        if(!boards.isEmpty()){
            return boards;
        }
        else {
            throw new DataNotFoundException("Data not found");
        }

    }

    // 멤버, 제목, 내용, 카테고리, 북마크(0), 태그
    public void create(Member member, String title, String content, // Create
                       category category, tag tag) {
        Board board = new Board();
        board.setMember(member);
        board.setTitle(title);
        board.setContent(content);
        //board.setSource_code(source_code); //필수 내용이 아님
        //board.setImage(image); // 필수 내용이 아님
        board.setCategory(category);
        board.setBookmark_cnt(0); // 생성 당시의 북마크된 개수는 무조건 0.
        board.setTag(tag);
        board.setWrite_date(LocalDateTime.now());
        //board.setModified_date(modified_date); // 필수 내용이 아님

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


    public Page<Board> getList(int page) {  // 페이지 8개의 게시물 리스트로 보여주는 부분
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("write_date")); // 작성 일시를 기준으로 내림차순
        Pageable pageable = PageRequest.of(page, 8, Sort.by(list));
        return this.boardRepository.findAll(pageable);
    }

    public Page<Board> getListByCategory(int page, category category) { // 카테고리 별로 탐색
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("write_date"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(list));
        return this.boardRepository.findByCategory(pageable, category);
    }

    public Page<Board> getListByTag(int page, tag tag) { // 태그 별로 탐색
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("write_date"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(list));
        return this.boardRepository.findByTag(pageable, tag);
    }
}
