package com.Project.BackEnd.Comment.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    private String content;
    private String name;
    private String board_id;
}
