package com.Project.BackEnd.BookMark.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/*
*** 프로필 조회 목적
 */
@Getter
public class BookMarkDetailDTO {

    private long id;
    private String boardTitle;

    @JsonCreator
    public BookMarkDetailDTO(long id, String boardTitle){
        this.id = id;
        this.boardTitle = boardTitle;
    }
}
