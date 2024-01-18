package com.Project.BackEnd.Board.Form;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    @NotEmpty(message = "제목은 필수 항목 입니다.")
    @Size(max = 200)
    private String title;

    @NotEmpty(message = "내용을 입력하십시오.")
    @Size(max = 3000)
    private String content;
}
