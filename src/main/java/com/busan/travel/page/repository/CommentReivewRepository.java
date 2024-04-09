package com.busan.travel.page.repository;

import com.busan.travel.page.entity.CommentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReivewRepository extends JpaRepository<CommentReview, Long> {
    @Override
    Optional<CommentReview> findById(Long id);
}
