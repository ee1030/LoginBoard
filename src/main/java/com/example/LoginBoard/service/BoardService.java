package com.example.LoginBoard.service;

import com.example.LoginBoard.domain.entity.Board;
import com.example.LoginBoard.domain.entity.MemberEntity;
import com.example.LoginBoard.domain.repository.BoardRepository;
import com.example.LoginBoard.domain.repository.MemberRepository;
import com.example.LoginBoard.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4; // 한 페이지에 존하는 게시글 수

    @Transactional
    public List<BoardDto> getBoardList(Integer pageNum) {
        Page<Board> page = boardRepository.findAll(
                PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(
                        Sort.Direction.DESC, "createdDate"))); // 최신글부터 볼수 있도록 내림차순으로 정렬
        List<Board> boards = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        //총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        //총 게시글을 기준으로 계산한 마지막 페이지 번호 계산(올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum -2;

        // 페이지 번호 할당
        for(int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
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

        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();
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
