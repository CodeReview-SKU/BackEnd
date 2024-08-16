package com.Project.BackEnd.Board.Dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardInfoDTO {
    private long id;
    private String title;
    private String name;
    private String content;
    private LocalDateTime write_date;
    private int likeCount;

    public BoardInfoDTO(long id, String title, String name, String content, LocalDateTime write_date, int likeCount) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.content = content;
        this.write_date = write_date;
        this.likeCount = likeCount;
    }
}
