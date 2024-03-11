package com.busan.travel.controller;

import com.busan.travel.entity.Board;
import com.busan.travel.entity.Member;
import com.busan.travel.service.BoardService;
import com.busan.travel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/")
    public String home(Principal principal, Model model,
                       @RequestParam(value = "sort", defaultValue = "") String sort){
        List<Board> boardList =boardService.getListMain(sort);
        model.addAttribute("boardList", boardList); // 자유게시판 리스트

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
