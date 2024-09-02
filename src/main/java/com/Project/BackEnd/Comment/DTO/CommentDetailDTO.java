package com.Project.BackEnd.Comment.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDateTime;


/*
*** 프로필 페이지에서 정보 조회할 때 사용 목적
 */
@Getter
public class CommentDetailDTO {

    private long id;
    private String content;
    private String boardTitle;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @JsonCreator
    public CommentDetailDTO(long id, String content, String boardTitle, LocalDateTime createDate, LocalDateTime modifyDate){
        this.id = id;
        this.content = content;
        this.boardTitle = boardTitle;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
