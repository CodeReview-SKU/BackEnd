package com.Project.BackEnd.Member.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private accountProvider accountProvider;

    public enum accountProvider{
        GOOGLE("구글"),
        NAVER("네이버"),
        KAKAO("카카오");

        private String accountProvider;

        accountProvider(String accountProvider){
            this.accountProvider = accountProvider;
        }

        public String getAccountProvider() {
            return accountProvider;
        }
    }

    private String email;

    private String userId;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private role role;

    public enum role{
        USER("일반"),
        ADMIN("관리자");

        private String role;

        role(String role){
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    private String refreshToken;

    @Builder
    public Member(String name, String email, accountProvider accountProvider, role role) {
        this.name = name;
        this.accountProvider = accountProvider;
        this.email = email;
        this.role = role;
    }

    @Builder
    public Member(String userId, String password, String name, String email, role role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
