package com.Project.BackEnd.Member.DTO;

import com.Project.BackEnd.Member.Entity.Member.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberDTO {
    private long id;
    private String name;
    private String email;
    private String userId;
    private String password;

    @JsonCreator
    public MemberDTO(long id, String name, String email, String userId){
        this.id = id;
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

}
