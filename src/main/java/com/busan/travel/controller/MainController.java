package com.busan.travel.controller;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.User;
import com.busan.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Principal principal, Model model){
        if(principal == null)
            return "layout/MainLayout";
        Optional<User> op =userService.getUser(principal.getName());
        if(op.isPresent()){
           User user = op.get();
            model.addAttribute("user", user);
        }
        return "layout/MainLayout";
    }

  }
