package com.Project.BackEnd.BookMark.DTO;


import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.BookMark.Entity.BookMark;
import com.Project.BackEnd.Member.Entity.Member;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookMarkDTO {

    private String member;
    private String board;
}
