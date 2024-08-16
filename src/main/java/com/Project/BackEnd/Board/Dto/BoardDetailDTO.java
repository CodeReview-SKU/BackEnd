package com.Project.BackEnd.Board.Dto;

import com.Project.BackEnd.Board.Entity.Board;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class BoardDetailDTO {
    private long id;
    private String title;
    private String name;
    private String content;
    private String source_code;
    private String image;
    private Board.category category;
    private Board.tag tag;
    private LocalDateTime write_date;
    private LocalDateTime modified_date;
    private int likeCount;

    @JsonCreator
    public BoardDetailDTO(long id, String title, String name, String content, String source_code, String image, Board.category category, Board.tag tag, LocalDateTime write_date, LocalDateTime modified_date, int likeCount) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.content = content;
        this.source_code = source_code;
        this.image = image;
        this.category = category;
        this.tag = tag;
        this.write_date = write_date;
        this.modified_date = modified_date;
        this.likeCount = likeCount;
    }
}
