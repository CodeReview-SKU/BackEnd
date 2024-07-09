package com.Project.BackEnd.Board.Dto;


import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Board.Entity.Board.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
public class BoardDTO {
    private String title;
    private String member_id;
    private String content;
    private tag tag;
    private category category;


}
