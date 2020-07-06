package com.example.LoginBoard.dto;

import com.example.LoginBoard.domain.entity.Board;
import com.example.LoginBoard.domain.entity.MemberEntity;
import com.example.LoginBoard.domain.repository.MemberRepository;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private MemberRepository memberRepository;

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long memberEntityId;
    private MemberEntity loginMemberEntity;

    public Board toEntity() {
        Board board = Board.builder()
                .id(id)
                .content(content)
                .title(title)
                .writer(writer)
                .memberEntity(loginMemberEntity)
                .build();
        return board;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer,
                    LocalDateTime createdDate, LocalDateTime modifiedDate, Long memberEntityId) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.memberEntityId = memberEntityId;
    }

}
