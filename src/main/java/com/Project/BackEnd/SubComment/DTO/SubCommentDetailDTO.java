package com.Project.BackEnd.SubComment.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDateTime;

/*
*** 프로필에서 조회 목적
 */
@Getter
public class SubCommentDetailDTO {

    private long id;
    private String boardTitle;
    private String comment;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @JsonCreator
    public SubCommentDetailDTO(long id, String boardTitle, String comment, LocalDateTime createDate, LocalDateTime modifyDate) {

        this.id = id;
        this.boardTitle = boardTitle;
        this.comment = comment;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
