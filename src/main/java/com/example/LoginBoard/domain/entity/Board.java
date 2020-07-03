package com.example.LoginBoard.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board")
public class Board extends Time{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length =  10, nullable = false)
    private String writer;

    @Column(length =  100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    private MemberEntity memberEntity;

    @Builder
    public Board(Long id, String title, String content, String writer, MemberEntity memberEntity) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.memberEntity = memberEntity;
    }
}
