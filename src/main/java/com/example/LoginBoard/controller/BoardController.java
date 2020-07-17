package com.example.LoginBoard.controller;

import com.example.LoginBoard.domain.NonMember;
import com.example.LoginBoard.domain.entity.MemberEntity;
import com.example.LoginBoard.dto.BoardDto;
import com.example.LoginBoard.service.BoardService;
import com.example.LoginBoard.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;
    private MemberService memberService;

    //메인 페이지
    @GetMapping("/")
    public String index(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum)
    {
        List<BoardDto> boardList = boardService.getBoardList(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        return "index";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable Long id, Model model) {
        if (memberService.isLogin()) {
            MemberEntity loginUser = memberService.getLoginUser();
            model.addAttribute("loginUser", loginUser);
        } else {
            NonMember loginUser = new NonMember();
            model.addAttribute("loginUser", loginUser);
        }

        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto", boardDto);
        return "board/detail.html";
    }

    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);

        model.addAttribute("boardDto", boardDto);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "index";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable Long id) {
        boardService.deletePost(id);

        return "redirect:/";
    }
}
