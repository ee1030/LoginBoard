package com.example.LoginBoard.controller;

import com.example.LoginBoard.domain.entity.MemberEntity;
import com.example.LoginBoard.dto.MemberDto;
import com.example.LoginBoard.service.BoardService;
import com.example.LoginBoard.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
public class MemberController {
    private BoardService boardService;
    private MemberService memberService;
    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup(MemberDto memberDto) {
        return "/signup";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 회원가입 실패시 입력 테이터 유지
            model.addAttribute("memberDto", memberDto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/signup";
        }

        if(memberService.checkEmail(memberDto.getEmail())) {
            return "/emailExisted";
        }
        memberService.joinUser(memberDto);

        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo(Model model) {
        MemberEntity loginUser = memberService.getLoginUser();
        model.addAttribute("loginUser", loginUser);
        return "/myinfo";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin(Model model) {
        MemberEntity loginUser = memberService.getLoginUser();
        model.addAttribute("loginUser", loginUser);
        return "/admin";
    }

    @DeleteMapping("/user/info/delete")
    public String delete() {
        MemberEntity loginUser = memberService.getLoginUser();
        memberService.deleteMember(loginUser.getId());
        return "/deleteSuccess";
    }

    @GetMapping("/test")
    public String test() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == "anonymousUser")
        log.info("{}", principal);
        return "/index";
    }
}
