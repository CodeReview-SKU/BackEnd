package com.Project.BackEnd.Board.Controller;

import com.Project.BackEnd.Board.Dto.BoardDTO;
import com.Project.BackEnd.Board.Dto.BoardDetailDTO;
import com.Project.BackEnd.Board.Dto.BoardInfoDTO;
import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Entity.Board.*;
import com.Project.BackEnd.Board.Service.BoardService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/list") //게시물 리스트 뷰어 컨트롤러
    public ResponseEntity<List<BoardInfoDTO>> list() {
        List<BoardInfoDTO> boardList = this.boardService.getBoardList();
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/detail/{id}") //게시물 상세 내용을 보여주는 컨트롤러
    public ResponseEntity<BoardDetailDTO> detail(@PathVariable long id) {
        try{
            BoardDetailDTO board = this.boardService.getBoardDetail(id);
            return ResponseEntity.ok(board);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<String> create(@RequestBody BoardDTO boardDTO)
    {
        try{
            Member member = memberService.getMemberById(Long.parseLong(boardDTO.getMember_id()));
            boardService.create(member,
                    boardDTO.getTitle(),
                    boardDTO.getContent(),
                    boardDTO.getSource_code(),
                    category.fromString(boardDTO.getCategory()),
                    tag.fromString(boardDTO.getTag())) ;
            return ResponseEntity.ok("created success");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/detail/{id}")
    public ResponseEntity<List<BoardInfoDTO>> modify(@PathVariable long id, @RequestBody BoardDTO boardDTO) {
        Board board = boardService.getBoard(id);

        boardService.update(board,
                boardDTO.getTitle(),
                boardDTO.getContent(),
                category.fromString(boardDTO.getCategory()),
                tag.fromString(boardDTO.getTag()));

        return ResponseEntity.ok(boardService.getBoardList());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            boardService.delete(boardService.getBoard(id));
            return ResponseEntity.ok("deleted");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<BoardInfoDTO>> getListByMemberId(@PathVariable String memberId) {
            //Member member = memberService.getMemberById(Long.parseLong(memberId));
        return ResponseEntity.ok(this.boardService.getBoardListByMember(Long.parseLong(memberId)));
    }

    // **** 이미지 추가 컨트롤러 추가 해야함. ****
}
