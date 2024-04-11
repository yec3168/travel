package com.busan.travel.page.controller;

import com.busan.travel.page.dto.CommentReviewFormDto;
import com.busan.travel.page.dto.CommentUpdateForm;
import com.busan.travel.page.entity.CommentReview;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import com.busan.travel.page.service.CommentReviewService;
import com.busan.travel.page.service.MemberService;
import com.busan.travel.page.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String writeComment(@Valid CommentReviewFormDto commentReviewFormDto,
                               @PathVariable("id")Long id, Principal principal){
        // review id, writer;
        Review review = reviewService.findReview(id);
        Member writer = memberService.getUserByEmail(principal.getName());

        CommentReview commentReview = commentReviewService.commentSave(writer, review, commentReviewFormDto);
        if(commentReview == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 권한이 없습니다./n 로그인후 이용해주세요.");
        }

        return "redirect:/review/detail/"+id;
    }
    @GetMapping("/update/{id}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public CommentUpdateForm updateComment(@PathVariable("id")Long id, Principal principal){
        CommentReview commentReview = commentReviewService.findComment(id);

        if(!commentReview.getWriter().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다./n 로그인후 이용해주세요.");
        }
        else{
            CommentUpdateForm commentUpdateForm = new CommentUpdateForm();
            commentUpdateForm.setNickName(commentReview.getWriter().getNickName());
            commentUpdateForm.setContent(commentReview.getContent());

            return commentUpdateForm;
        }
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateComment(@PathVariable("id")Long id, Principal principal,
                                @RequestParam("updateContent") String content){

        CommentReview commentReview = commentReviewService.findComment(id);
        // 비정상 접근 제한.
        if(!commentReview.getWriter().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다./n 로그인후 이용해주세요.");
        }

        // check
        System.out.println(content);

        // content update
        commentReviewService.updateContent(content, commentReview);

        return "redirect:/review/detail/"+commentReview.getReview().getId();
    }


    // comment delete
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteComment(@PathVariable("id")Long id, Principal principal){
        CommentReview commentReview = commentReviewService.findComment(id);
       
        if(!commentReview.getWriter().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다./n 로그인후 이용해주세요.");
        }
        commentReviewService.deleteComment(commentReview);

        return "redirect:/review/detail/"+ commentReview.getReview().getId();
    }
}
