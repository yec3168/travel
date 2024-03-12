package com.busan.travel.page.controller;

import com.busan.travel.page.entity.Board;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.service.BoardService;
import com.busan.travel.page.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @GetMapping("/")
    public String home(Principal principal, Model model,
                       @RequestParam(value = "boardSort", defaultValue = "") String boardSort,
                       @RequestParam(value = "reviewSort", defaultValue = "") String reviewSort){
        List<Board> boardList =boardService.getListMain(boardSort);
        model.addAttribute("boardList", boardList); // 자유게시판 리스트
        model.addAttribute("kakao_admin_key", kakao_admin_key);
        if(principal == null)
            return "layout/Home";
        else {
            Optional<Member> op = memberService.getUser(principal.getName());
           Member user = op.get();
            model.addAttribute("user", user);
        }
        return "layout/Home";
    }

  }
