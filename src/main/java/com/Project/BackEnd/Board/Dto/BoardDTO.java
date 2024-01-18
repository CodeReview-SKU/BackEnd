package com.Project.BackEnd.Board.Dto;


import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Board.Entity.Board.*;
import lombok.Getter;

import java.time.LocalDateTime;

public class BoardDTO {
    @Getter
    private long id;
    private Member member;
    private String title;
    private String content;
    private String source_code;
    private String image;
    private category category;
    private int bookmark_cnt;
    private tag tag;
    private LocalDateTime write_date;
    private LocalDateTime modified_date;


    public BoardDTO(Board board) {
        this.id = board.getId();
        this.member = board.getMember();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.source_code = board.getSource_code();
        this.image = board.getImage();
        this.category = board.getCategory();
        this.bookmark_cnt = board.getBookmark_cnt();
        this.tag = board.getTag();
        this.write_date = board.getWrite_date();
        this.modified_date = board.getModified_date();
    }

    // 아직 미완. **

}
