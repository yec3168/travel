package com.busan.travel.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class PlannerController {

    @GetMapping("/view")
    public String view(){
        return "tourist/plan";
    }
}
