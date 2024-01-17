package com.Project.BackEnd.Board.Service;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Repository.BoardRepository;
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


    public Board getBoard(Long id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        }
        else {
            throw new RuntimeException("Data Not Found");
        }

    }

    public void create(Member member, String title, String content, //수정 할 필요 있음
                       category category, int bookmark_cnt, tag tag,
                       LocalDateTime write_date) {
        Board board = new Board();
        board.setMember(member);
        board.setTitle(title);
        board.setContent(content);
        //board.setSource_code(source_code);
        //board.setImage(image);
        board.setCategory(category);
        board.setBookmark_cnt(bookmark_cnt);
        board.setTag(tag);
        board.setWrite_date(write_date);
        //board.setModified_date(modified_date);

        this.boardRepository.save(board);
    }

    public void AddImage(Board board, String image) {
        board.setImage(image);
        this.boardRepository.save(board);
    }

    public void AddSourceCode(Board board, String source_code) {
        board.setSource_code(source_code);
        this.boardRepository.save(board);
    }

    public void AddModifiedDate(Board board, LocalDateTime modified_date) {
        board.setModified_date(modified_date);
        this.boardRepository.save(board);
    }

    public Page<Board> getList(int page) {  // 페이지 20개의 게시물 리스트로 보여주는 부분
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("write_date"));
        Pageable pageable = PageRequest.of(page, 20, Sort.by(list));
        return this.boardRepository.findAll(pageable);
    }
}
