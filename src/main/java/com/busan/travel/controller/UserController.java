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
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public String postNewUser(UserFormDto userFormDto, @RequestParam("userImg") MultipartFile multipartFile, Model model ){
        System.out.println("post됨~~~~~~~~~~~~~~~~~~~~~" + userFormDto.getPassword());
        try {
            userService.createUser(userFormDto);
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
    @ResponseBody
    public String getLoginError(@RequestParam("msg") String msg, Model model){
        model.addAttribute("msg", msg);

        return msg;
    }




}
