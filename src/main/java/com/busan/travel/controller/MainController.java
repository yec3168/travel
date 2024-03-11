package com.busan.travel.controller;

import com.busan.travel.entity.Member;
import com.busan.travel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String home(Principal principal, Model model){
        if(principal == null)
            return "layout/Home";
        Optional<Member> op = memberService.getUser(principal.getName());
        if(op.isPresent()){
           Member user = op.get();
            model.addAttribute("user", user);
        }
        return "layout/Home";
    }

  }
