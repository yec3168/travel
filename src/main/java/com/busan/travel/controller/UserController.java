package com.busan.travel.controller;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.User;
import com.busan.travel.repository.UserRepository;
import com.busan.travel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @PostMapping("/new")
    public String postNewUser(UserFormDto userFormDto, @RequestParam("userImg") MultipartFile multipartFile, Model model ){
        System.out.println("post됨~~~~~~~~~~~~~~~~~~~~~" + userFormDto.getPassword());
        try {
            userService.createUser(userFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/user/login";

    }

    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/UserLogin";
    }

    @GetMapping("/login/error")
    public String getLoginError(Model model){
        model.addAttribute("errorMsg", "아이디 혹은 비밀번호를 확인해주세요.");
        return "user/UserLogin";
    }




}
