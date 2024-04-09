package com.busan.travel.page.controller;

import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import com.busan.travel.page.service.CommentReviewService;
import com.busan.travel.page.service.MemberService;
import com.busan.travel.page.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/review/comment")
public class CommentReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CommentReviewService commentReviewService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/new/{id}")
    @PreAuthorize("isAuthenticated()")
    public String writeComment(@PathVariable("id")Long id, Principal principal){
        // review id;
        Review review = reviewService.findReview(id);
        Member writer = memberService.getUserByEmail(principal.getName());

    }
}
