package com.busan.travel.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @GetMapping("/list")
    public String viewList(){
        return "review/List";
    }

    @GetMapping("/write")
    public String reviewWrite(){

        return "review/Write";
    }
}
