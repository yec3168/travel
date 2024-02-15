package com.busan.travel.controller;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/new")
    public String postNewUser(@ModelAttribute UserFormDto userFormDto, @RequestParam("userImg") MultipartFile multipartFile, Model model ){
        try {
            userService.createUser(userFormDto, multipartFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/user/login";

    }

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
