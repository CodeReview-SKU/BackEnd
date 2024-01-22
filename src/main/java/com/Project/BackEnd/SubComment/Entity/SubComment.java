package com.Project.BackEnd.SubComment.Entity;

import com.Project.BackEnd.Comment.Entity.Comment;
import com.Project.BackEnd.Member.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SubComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(length = 1000)
    private String content;

    private int like_cnt;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
