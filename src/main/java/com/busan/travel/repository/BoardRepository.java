package com.busan.travel.repository;

import com.busan.travel.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);

    Page<Board> findAll(Pageable pageable);

    //게시판(공지) 최신순
    @Query(" SELECT b "
            + "FROM Board as b "
            + "WHERE b.noticeYn = true "
            + "order by b.createDate desc  "
    )
    List<Board> findByNoticeYnTrueOrderByCreateDateDesc();

    //게시판(자유) 최신순
    @Query(  "SELECT b "
            + "from Board as b "
            + "WHERE b.noticeYn =false "
            + "order by b.createDate desc ")
    Page<Board> findByNoticeYnFalseOrderByCreateDateDesc(Pageable pageable);


    //게시판 조회순 (메인 홈페이지)
    @Query( "SELECT b " +
            "FROM Board as b " +
            "WHERE b.noticeYn =false " +
            "ORDER BY b.view desc, b.createDate ")
    List<Board> findAllByOrderByView(Pageable pageable);

    //게시판 추천순 (메인 홈페이지)
    @Query( "SELECT b " +
            "FROM  Board as b " +
            "WHERE b.noticeYn =false "+
            "ORDER BY size(b.likeVote) desc, b.createDate ")
    List<Board> findAllByOrderByLikeVote(Pageable pageable);
}