package com.busan.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping("/new")
    public String getNewUser(){
        return "user/UserForm";
    }
    @PostMapping("/new")
    public String PostNewUser(){
        return "redirect:/";
    }
    @GetMapping("/login")
    public String getLogin(){
        return "user/UserLogin";
    }

    @PostMapping("/login")
    public String postLogin(){

        return "redirect:/";
    }
    @GetMapping("/login/error")
    public String getLoginError(){
        return "user/UserLogin";
    }



}
