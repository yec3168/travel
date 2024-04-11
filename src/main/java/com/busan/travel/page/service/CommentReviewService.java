package com.busan.travel.page.service;

import com.busan.travel.page.dto.CommentReviewFormDto;
import com.busan.travel.page.entity.CommentReview;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import com.busan.travel.page.repository.CommentReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentReviewService {

    @Autowired
    private CommentReviewRepository commentReviewRepository;

    @Autowired
    private ReviewService reviewService;

    public CommentReview findComment(Long id){
        Optional<CommentReview> op = commentReviewRepository.findById(id);
        return op.orElse(null);
    }
    public CommentReview commentSave(Member writer, Review review, CommentReviewFormDto commentReviewFormDto){
        CommentReview commentReview = CommentReview.builder()
                .content(commentReviewFormDto.getContent())
                .writer(writer)
                .review(review)
                .build();

        commentReviewRepository.save(commentReview);
        return commentReview;
    }

    public Page<CommentReview> findList(int page, String sort, Review review){
        Pageable pageable = PageRequest.of(page, 10);
        if(sort.equals("createDate") || sort.equals(""))
            return commentReviewRepository.findAllByOrderByCreateDateDesc(review, pageable);
        else
            return commentReviewRepository.findAllByOOrderByVoteD(review, pageable);
    }


    // content update
    public void updateContent(String content, CommentReview commentReview){
        commentReview.updateContent(content);
        commentReviewRepository.save(commentReview);
    }
    // content delete
    public void deleteComment(CommentReview commentReview){
        commentReviewRepository.delete(commentReview);
    }
}
