package com.Project.BackEnd;

import com.Project.BackEnd.Board.Controller.BoardController;
import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Board.Repository.BoardRepository;
import com.Project.BackEnd.Board.Service.BoardService;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import com.Project.BackEnd.Member.Service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BackEndApplicationTests {

	@Autowired
	BoardService boardService;

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	BoardRepository boardRepository;


	@Test
	public void testCreateMember() {
		memberService.create("admin", Member.accountProvider.GOOGLE, Member.role.ADMIN);
	}


	@Test
	public void testCreateBoard() {
		// Given
		Member member = memberService.getMember("admin");
		String title = "Test Title";
		String content = "Test Content 23";
		Board.category category = Board.category.NOTICE; // 적절한 Category 객체 생성
		Board.tag tag = Board.tag.GREEDY; // 적절한 Tag 객체 생성

		// When
		boardService.create(member, title, content, category, tag);

	}

	@Test
	public void testReadBoard() {
		long id = 5;
		Board board = this.boardService.getBoard(id);
		System.out.println(board.getTitle());
		System.out.println(board.getCategory());
		System.out.println(board.getMember().getName());
	}

	@Test
	public void testUpdateBoard() {
		long id = 6;
		Board board = this.boardService.getBoard(id);
		System.out.println(board.getTitle());
		System.out.println(board.getCategory());
		System.out.println(board.getMember().getName());
		System.out.println();

		this.boardService.update(board, "New", "몰라 ㅅㅂ", Board.category.NOTICE, Board.tag.GREEDY);
		board = this.boardService.getBoard(id);
		System.out.println(board.getTitle());
		System.out.println(board.getCategory());
		System.out.println(board.getMember().getName());


	}

	@Test
	public void testDeleteBoard() {
		long id = 6;
		Board board = this.boardService.getBoard(id);
		this.boardService.delete(board);
	}

}
