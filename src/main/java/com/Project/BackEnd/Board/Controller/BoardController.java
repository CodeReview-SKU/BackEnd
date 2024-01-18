package com.Project.BackEnd.Board.Controller;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;


    @GetMapping("/list") //게시물 리스트 뷰어 컨트롤러
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Board> boardPage = this.boardService.getList(page);
        model.addAttribute("boardPage", boardPage); //프론트엔드에 "boardPage" : boardPage(값)쌍으로 데이터 전달.
        return "board_list";
    }

    @GetMapping("/detail/{id}") //게시물 상세 내용을 보여주는 컨트롤러
    public String detail(Model model, @PathVariable("id") long id ) {//코멘트 폼 필요. **
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }


    // 여기에 인증된 사용자에 대한 메소들 작성.

}
