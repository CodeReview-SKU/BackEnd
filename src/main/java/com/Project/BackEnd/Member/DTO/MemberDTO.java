package com.Project.BackEnd.Member.DTO;

import com.Project.BackEnd.Member.Entity.Member.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class MemberDTO {
    private String name;
    private accountProvider accountProvider;
    private role role;

}
