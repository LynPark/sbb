package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    //질문을 참조(외래키)
    @ManyToOne
    private Question question; //질문 하나에 답변 여러개 관계

    @ManyToOne
    private SiteUser author; //한 명이 여러 개의 답변 가능

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;
}
