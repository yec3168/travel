package com.busan.travel.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegionController {

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("kakao_admin_key", kakao_admin_key);
        return "region/example";
    }
}
