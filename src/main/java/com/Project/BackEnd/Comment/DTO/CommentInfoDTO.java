package com.Project.BackEnd.Comment.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDateTime;

/*
*** 보드 댓글 조회 목적
 */
@Getter
public class CommentInfoDTO {

    private long id;
    private String content;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @JsonCreator
    public CommentInfoDTO(long id, String content, String name, LocalDateTime createDate, LocalDateTime modifyDate){
        this.id = id;
        this.content = content;
        this.name = name;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
