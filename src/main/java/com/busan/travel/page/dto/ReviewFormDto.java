package com.busan.travel.page.dto;

import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFormDto {

    private String subject; // 제목
    private String content; // 내용
    private int stars; // 평점;
    private String thumbnail; // 썸네일
    private String url; //저장 주소.


    public static ReviewFormDto toDto(Review review){
        ReviewFormDto reviewFormDto = new ReviewFormDto();
        reviewFormDto.setSubject(review.getSubject());
        reviewFormDto.setContent(review.getContent());
        reviewFormDto.setStars(review.getStars());
        reviewFormDto.setThumbnail(review.getThumbnail());
        reviewFormDto.setUrl(review.getUrl());

        return  reviewFormDto;
    }
}
