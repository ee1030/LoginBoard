package com.example.LoginBoard.dto;

import com.example.LoginBoard.domain.entity.Board;
import com.example.LoginBoard.domain.entity.MemberEntity;
import com.example.LoginBoard.domain.repository.MemberRepository;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long memberEntityId;

    public Board toEntity() {
        Board board = Board.builder()
                .id(id)
                .content(content)
                .title(title)
                .writer(writer)
                .build();
        return board;
    }

    public String currentMemberName() {
        MemberDto memberDto = (MemberDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberDto.getEmail();
    } //TODO : 로그인된 유저정보 가져오기

    @Builder
    public BoardDto(Long id, String title, String content,
                    LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.writer = currentMemberName();
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
