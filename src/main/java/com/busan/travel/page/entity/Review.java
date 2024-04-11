package com.busan.travel.page.entity;

import com.busan.travel.page.dto.ReviewFormDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    
    private String subject; // 제목
    @Column(length = 1000)
    private String content; // 내용
    private int stars; // 평점;
    private String thumbnail; // 썸네일
    private String url; //저장 주소.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer; // 작성자

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    List<CommentReview> commentReviewList;

    @Builder
    private Review(String subject, String content, int stars,
                   String thumbnail, String url, Member writer){
        this.subject =subject;
        this.content = content;
        this.stars = stars;
        this.thumbnail = thumbnail;
        this.url = url;
        this.writer = writer;
    }

    public void updateContent(ReviewFormDto reviewFormDto){
        this.subject = reviewFormDto.getSubject();
        this.content = reviewFormDto.getContent();
        this.stars = reviewFormDto.getStars();
        this.thumbnail = reviewFormDto.getThumbnail();
        this.url = reviewFormDto.getUrl();
    }
}
