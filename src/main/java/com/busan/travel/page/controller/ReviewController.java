package com.busan.travel.page.controller;

import com.busan.travel.page.dto.ReviewFormDto;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import com.busan.travel.page.service.MemberService;
import com.busan.travel.page.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(value = "page", defaultValue = "0")int page){
        Page<Review> paging = reviewService.findAllByCreateDate(page);
        model.addAttribute("paging", paging);
        return "review/List";
    }

    @GetMapping("/write")
    public String reviewWrite(Model model, Principal principal){
        if (principal == null) {
            model.addAttribute("msg", "로그인후 이용해 주세요.");
            return "user/UserLogin";
        }
        model.addAttribute("reviewFormDto", new ReviewFormDto());
        return "review/Write";
    }

    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String reviewWrite(@Valid ReviewFormDto reviewFormDto, Model model,
                              @RequestParam("thumb") MultipartFile multipartFile,
                              Principal principal){

        if(principal != null){
            Member writer = memberService.getUserByEmail(principal.getName());
            reviewService.saveReview(reviewFormDto, writer, multipartFile);

        }
        return "redirect:/review/list";
    }
}
