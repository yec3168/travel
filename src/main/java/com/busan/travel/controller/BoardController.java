package com.busan.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/list")
    public String getBoardList(){
        return "board/List";
    }

    @GetMapping("/write")
    public String getBoardWrite(){
        return "board/Write";
    }
}
