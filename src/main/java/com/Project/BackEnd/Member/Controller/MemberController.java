package com.Project.BackEnd.Member.Controller;


import com.Project.BackEnd.Member.DTO.MemberDTO;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public String signUp(@RequestBody MemberDTO memberDTO) throws Exception {
        memberService.create(memberDTO);

        return "Success sign up";
    }


    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "Jwt success";
    }
}
