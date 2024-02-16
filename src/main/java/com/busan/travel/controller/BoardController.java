package com.busan.travel.controller;

import com.busan.travel.dto.BoardFormDto;
import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.User;
import com.busan.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getBoardList(){
        return "board/List";
    }

    @GetMapping("/write")
    public String getBoardWrite(Model model, Principal principal){

        if(principal == null){
            model.addAttribute("msg", "로그인후 이용해 주세요.");
            return "user/UserLogin";
        }
        Optional<User> op =userService.getUser(principal.getName());
        if(op.isPresent()){
            UserFormDto userFormDto = UserFormDto.toDto(op.get());
            model.addAttribute("userFormDto", userFormDto);
        }
        return "board/Write";

    }
}
