package com.Project.BackEnd.Board.Entity;

import com.Project.BackEnd.Member.Entity.Member;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    //@Size(min = 1, max = 200, message = "제목의 글자수는 1 ~ 200자로 작성하십시오.")
    @Column(length = 200, nullable = false)
    private String title;    //@Size(min = 1, max = 3000, message = "본문의 글자수는 1 ~ 3000자로 작성하십시오.")

    @Column(length = 3000, nullable = false)
    private String content;

    @Column(columnDefinition = "mediumtext")
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

        @JsonValue
        public String getCategory() {
            return category;
        }

        @JsonCreator
        public static category fromString(String s) {
            for (category t : values()) {
                if (t.category.equalsIgnoreCase(s)) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Unknown label: " + s);
        }
    }

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

        private String label;


        tag(String tag) {
                this.label = tag;
            }

        @JsonValue
        public String getTag() { return label; }
        @JsonCreator
        public static tag fromString(String text) {
            for (tag t : values()) {
                if (t.label.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Unknown label: " + text);
        }
    }

    private LocalDateTime write_date;

    private LocalDateTime modified_date;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int likeCount;

    @PreUpdate //수정 시 일어나는 메소드 정의
    public void preUpdate(){
        this.modified_date = LocalDateTime.now();
    }

}
