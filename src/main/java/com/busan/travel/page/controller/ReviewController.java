package com.busan.travel.page.controller;

import com.busan.travel.page.dto.CommentReviewFormDto;
import com.busan.travel.page.dto.ReviewFormDto;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import com.busan.travel.page.service.CommentReviewService;
import com.busan.travel.page.service.MemberService;
import com.busan.travel.page.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CommentReviewService commentReviewService;


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


    @GetMapping("/detail/{id}")
    public String detailReview(@PathVariable("id")Long id, Model model,
                               @RequestParam(value = "sort", defaultValue = "")String sort,
                               @RequestParam(value = "page", defaultValue = "0")int page) {
        Review review = reviewService.findReview(id);
        model.addAttribute("review", review);

        // div 오른쪽 최신생성글 보여주기.
        model.addAttribute("recently_review", reviewService.recently_review());

        // review comment
        model.addAttribute("commnetReviewFormDto", new CommentReviewFormDto());

        // CommentReview List
        model.addAttribute("paging", commentReviewService.findList(page, sort, review));

        return "review/detail";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateReview(@PathVariable("id") Long id ,Principal principal,
                               Model model){
        Review review = reviewService.findReview(id);

        if(!review.getWriter().getEmail().equals(principal.getName())){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다./n 로그인후 이용해주세요.");
        }
        else{
            model.addAttribute("reviewFormDto", reviewService.changeDto(review));
            return "review/Write";
        }
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateReview(@Valid ReviewFormDto reviewFormDto,
                               @PathVariable("id")Long id, Principal principal,
                               @RequestParam("thumb") MultipartFile multipartFile){
        Review review = reviewService.findReview(id);
        if(!review.getWriter().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다./n 로그인후 이용해주세요.");
        }
        else{
            reviewService.updateReview(review, reviewFormDto, multipartFile);
        }
        return "redirect:/review/detail/"+ id;
    }
    
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteReview(@PathVariable("id")Long id, Principal principal){
        Review review = reviewService.findReview(id);
        if(!review.getWriter().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다./n 로그인후 이용해주세요.");
        }
        else{
            reviewService.delete(review);
        }
        return "redirect:/review/list";
    }


    @GetMapping("/search")
    public String searchKeyword(@RequestParam(value = "keyword", defaultValue = "")String keyword,
                                @RequestParam(value = "searchType", defaultValue = "") String searchType,
                                @RequestParam(value = "page", defaultValue = "0")int page, Model model) throws UnsupportedEncodingException {

        Page<Review> paging = reviewService.searchKeyword(page, keyword, searchType);

        model.addAttribute("paging",paging);
        return "review/List";
    }
}
