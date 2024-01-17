package com.Project.BackEnd.Board.Entity;


import com.Project.BackEnd.Member.Entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Size(min = 1, max = 200, message = "제목의 글자수는 1 ~ 200자로 작성하십시오.")
    @Column(length = 200, nullable = false)
    private String title;

    @Size(min = 1, max = 3000, message = "본문의 글자수는 1 ~ 3000자로 작성하십시오.")
    @Column(length = 3000, nullable = false)
    private String content;

    private String source_code;

    private String image;

    @Enumerated(value = EnumType.STRING)
    private category category;

    public enum category {
        NOTICE("공지"),
        REVIEW("리뷰"),
        QUESTION("질문");

        private String category;

        category(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        private int bookmark_cnt;

        @Enumerated(value = EnumType.STRING)
        private tag tag;

        public enum tag {
            DP("다이나믹 프로그래밍"),
            DATASTRUCTURE("자료구조"),
            GRAPH("그래프 이론"),
            MATH("수학"),
            IMPLEMENTATION("구현"),
            STRING("문자열"),
            GREEDY("그리디"),
            TREE("트리"),
            HASH("해시"),
            SORT("정렬");

            private String tag;

            tag(String tag) {
                this.tag = tag;
            }

            public String getTag() {
                return tag;
            }
        }

        private LocalDateTime write_date;

        private LocalDateTime modified_date;

    }
}
