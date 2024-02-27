package com.busan.travel.controller;

import com.busan.travel.dto.MemberFormDto;
import com.busan.travel.entity.Member;
import com.busan.travel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @PostMapping("/new")
    public String postNewUser(@ModelAttribute MemberFormDto memberFormDto, @RequestParam("userImg") MultipartFile multipartFile, Model model ){
        try {
            memberService.createUser(memberFormDto, multipartFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/user/login";

    }
    @GetMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("email") String email){
        Optional<Member> userOptional = memberService.getUser(email);
        if (userOptional.isPresent()) {
            return 1;

        } else {
            return 0;
        }
    }



    /*로그인 관련 controller*/
    @GetMapping("/login")
    public String getLogin(){
        return "user/UserLogin";
    }

    @GetMapping("/login/error")
    public String getLoginError(@RequestParam("msg") String msg, Model model){

        model.addAttribute("msg", msg);

        return "user/UserLogin";
    }





}
