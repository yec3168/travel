package com.busan.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegionController {
    @GetMapping("/test")
    public String test(){
        return "region/exam";
    }
}
