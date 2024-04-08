package com.busan.travel.page.repository;

import com.busan.travel.page.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    //게시판(리뷰) 최신순
    Page<Review> findByOrderByCreateDate(Pageable pageable);

}
