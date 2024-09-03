package com.Project.BackEnd.SubComment.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDateTime;

/*
*** 보드 대댓글 조회 목적
 */
@Getter
public class SubCommentInfoDTO {

    private long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @JsonCreator
    public SubCommentInfoDTO(long id, String content, LocalDateTime createDate, LocalDateTime modifyDate) {

        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
