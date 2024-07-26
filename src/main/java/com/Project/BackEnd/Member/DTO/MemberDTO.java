package com.Project.BackEnd.Member.DTO;

import com.Project.BackEnd.Member.Entity.Member.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberDTO {
    private String name;
    private String userId;
    private String password;

}
