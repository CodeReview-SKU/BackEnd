package com.Project.BackEnd.Board.Controller;

import com.Project.BackEnd.Board.Dto.BoardDTO;
import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Entity.Board.*;
import com.Project.BackEnd.Board.Form.BoardForm;
import com.Project.BackEnd.Board.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/list") //게시물 리스트 뷰어 컨트롤러
    public ResponseEntity<List<Board>> list() {
        List<Board> boardList = this.boardService.getBoardList();
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/detail/{id}") //게시물 상세 내용을 보여주는 컨트롤러
    public ResponseEntity<Board> detail(@PathVariable long id) {//코멘트 폼 필요. **
        Board board = this.boardService.getBoard(id);
        return ResponseEntity.ok(board);
    }

    @ResponseBody
    @PostMapping(value = "/detail")
    public ResponseEntity<List<Board>> create(@RequestBody BoardDTO boardDTO)
    {
        boardService.create(boardDTO.getMember(), boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getCategory(), boardDTO.getTag());

        return ResponseEntity.ok(boardService.getBoardList());
    }

    @PutMapping(value = "/detail/{id}")
    public ResponseEntity<List<Board>> modify(@PathVariable long id, @RequestBody BoardDTO boardDTO) {
        Board board = boardService.getBoard(id);
        boardService.update(board, boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getCategory(), boardDTO.getTag());

        return ResponseEntity.ok(boardService.getBoardList());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<List<Board>> delete(@PathVariable long id) {
        boardService.delete(boardService.getBoard(id));

        return ResponseEntity.ok(boardService.getBoardList());
    }

    @PutMapping(value = "/detail/{id}/code")
    public ResponseEntity<Board> addSourceCode(@PathVariable long id, @RequestBody BoardDTO boardDTO) {
        Board board = boardService.getBoard(id);
        boardService.addSourceCode(board, boardDTO.getSource_code());

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @GetMapping(value = "/list/tag/{tag}")
    public ResponseEntity<List<Board>> listTag(@PathVariable tag tag) {
        List<Board> list = boardService.getBoardListByTag(tag);

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/list/category/{category}")
    public ResponseEntity<List<Board>> listCategory(@PathVariable category category) {
        List<Board> list = boardService.getBoardListByCategory(category);

        return ResponseEntity.ok(list);
    }
    // **** 이미지 추가 컨트롤러 추가 해야함. ****
}
