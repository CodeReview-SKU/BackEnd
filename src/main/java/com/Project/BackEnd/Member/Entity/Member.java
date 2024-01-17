package com.Project.BackEnd.Member.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private accountProvider accountProvider;

    public enum accountProvider{
        GOOGLE("구글"),
        KAKAO("카카오");

        private String accountProvider;

        accountProvider(String accountProvider){
            this.accountProvider = accountProvider;
        }

        public String getAccountProvider() {
            return accountProvider;
        }
    }

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
}
