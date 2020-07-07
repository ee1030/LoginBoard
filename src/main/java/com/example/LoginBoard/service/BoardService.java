package com.example.LoginBoard.service;

import com.example.LoginBoard.domain.entity.Board;
import com.example.LoginBoard.domain.entity.MemberEntity;
import com.example.LoginBoard.domain.repository.BoardRepository;
import com.example.LoginBoard.domain.repository.MemberRepository;
import com.example.LoginBoard.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;

    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
            List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boards) {
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .writer(board.getWriter())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        Optional<MemberEntity> userWrapper = memberRepository.findByEmail(userDetails.getUsername());
        MemberEntity userEntity = userWrapper.get();

        boardDto.setWriter(userDetails.getUsername());
        boardDto.setMemberEntityId(userEntity.getId());
        boardDto.setLoginMemberEntity(memberRepository.getOne(userEntity.getId()));
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();

        return boardDto;
    }

    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if(boards.isEmpty()){
            return boardDtoList;
        }

        for(Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    private BoardDto convertEntityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
