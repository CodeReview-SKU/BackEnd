package com.Project.BackEnd.Member.Controller;


import com.Project.BackEnd.Member.DTO.LoginDTO;
import com.Project.BackEnd.Member.DTO.MemberDTO;
import com.Project.BackEnd.Member.DTO.loginInfo;
import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    @PostMapping("/register")
    public String signUp(@RequestBody MemberDTO memberDTO) throws Exception {
        memberService.create(memberDTO.getUserId(), memberDTO.getPassword(), memberDTO.getName());
        return "Success sign up";
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(memberService.getMemberById((id)));
    }


    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody LoginDTO loginDTO) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            loginInfo info = memberService.login(loginDTO.getUserId(), loginDTO.getPassword());
            responseBody.put("token", info.getToken());
            responseBody.put("name", info.getName());
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }


    @GetMapping("/check/email/{userId}")
    public ResponseEntity<Boolean> checkMember(@PathVariable String userId) {
        log.info("check");
        return ResponseEntity.ok(memberService.getMemberByUserId(userId) == null);
    }

    @GetMapping("/check/name/{name}")
    public ResponseEntity<Boolean> checkMemberByName(@PathVariable String name) {
        return ResponseEntity.ok(memberService.getMember(name) == null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Member> getMemberByUserId(@PathVariable String userId) throws Exception {
        return ResponseEntity.ok(memberService.getMemberByUserId(userId));
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "Jwt success";
    }
}
