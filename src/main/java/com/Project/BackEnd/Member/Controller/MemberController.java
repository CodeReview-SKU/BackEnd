package com.Project.BackEnd.Member.Controller;


import com.Project.BackEnd.Member.DTO.MemberDTO;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public String signUp(@RequestBody MemberDTO memberDTO) throws Exception {
        memberService.create(memberDTO);

        return "Success sign up";
    }
    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(memberService.getMemberById((id)));
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "Jwt success";
    }
}
