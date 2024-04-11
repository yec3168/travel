package com.busan.travel.page.repository;

import com.busan.travel.page.entity.Board;
import com.busan.travel.page.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(Long id);

    //게시판(리뷰) 최신순
    Page<Review> findByOrderByCreateDateDesc(Pageable pageable);

    List<Review> findByOrderByCreateDate();


    /* 검색기능 */
    
    // 1. 전체
    @Query("select r " +
            "from Review  as r " +
            "where r.subject like %:keyword% or " +
            "r.content like %:keyword% or " +
            "r.writer.nickName like %:keyword% " +
            "order by r.createDate desc ")
    Page<Review> findAllBySubjectOrContentOrWriterContaining(String keyword, Pageable pageable);

    // 2.  제목
    @Query("SELECT r " +
            "FROM  Review as r " +
            "where r.subject like  %:keyword% " +
            "order by r.createDate desc ")
    Page<Review> findBySubjectContaining(String keyword, Pageable pageable);


    // 3. 내용\
    @Query("SELECT r " +
            "FROM  Review as r " +
            "where r.content like  %:keyword% " +
            "order by r.createDate desc ")
    Page<Review> findByContentContaining(String keyword, Pageable pageable);

    // 4. 작성자
    @Query("SELECT r " +
            "FROM  Review  as r " +
            "where r.writer.nickName like %:keyword% " +
            "order by r.createDate desc")
    Page<Review> findByNickname(String keyword, Pageable  pageable);
}
