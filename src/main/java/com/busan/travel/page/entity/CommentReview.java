package com.busan.travel.page.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentReview extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_review_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;


    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public CommentReview(String content, Member writer, Review review){
        this.content = content;
        this.writer =writer;
        this.review = review;
    }
}
