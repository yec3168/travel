package com.busan.travel.controller;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/new")
    @ResponseBody
    public String postNewUser(@ModelAttribute UserFormDto userFormDto, MultipartFile multipartFile, Model model ){


        try{
            userService.createUser(userFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
        }
        return userFormDto.toString();

    }

    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/UserLogin";
    }
    @PostMapping("/login")

    public String postLogin(HttpServletRequest request ){
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("email :      " +email);
        System.out.println("password :      "+ password);
        return "redirect:/";
    }



    @GetMapping("/login/error")
    public String getLoginError(Model model){
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/UserLogin";
    }




}
