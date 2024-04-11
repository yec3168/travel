package com.busan.travel.page.repository;

import com.busan.travel.page.entity.CommentReview;
import com.busan.travel.page.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReviewRepository extends JpaRepository<CommentReview, Long> {
    @Override
    Optional<CommentReview> findById(Long id);

    @Query("select c " +
            "from CommentReview as c " +
            "where c.review = :review "+
            "order by c.createDate desc ")
    Page<CommentReview> findAllByOrderByCreateDateDesc(Review review, Pageable pageable);

    @Query("select c " +
            "from CommentReview as c " +
            "where c.review = :review "+
            "order by SIZE(c.vote) desc, c.createDate ")
    Page<CommentReview> findAllByOOrderByVoteD(Review review, Pageable pageable);

}
