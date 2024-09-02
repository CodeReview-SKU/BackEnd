package com.Project.BackEnd.Comment.Entity;

import com.Project.BackEnd.Board.Entity.Board;
import com.Project.BackEnd.Member.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String boardTitle;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 1000)
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
