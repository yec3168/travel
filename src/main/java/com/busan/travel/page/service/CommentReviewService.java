package com.busan.travel.page.service;

import com.busan.travel.page.dto.CommentReviewFormDto;
import com.busan.travel.page.entity.CommentReview;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.repository.CommentReivewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentReviewService {

    @Autowired
    private CommentReivewRepository commentReivewRepository;

    public void commentSave(Member writer, CommentReviewFormDto commentReviewFormDto){
        CommentReview commentReview = CommentReview.builder()
                .content(commentReviewFormDto.getContent())
                .writer(writer)
                .build();

        commentReivewRepository.save(commentReview);
    }
}
