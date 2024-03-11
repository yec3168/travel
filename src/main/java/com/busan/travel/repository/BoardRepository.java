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


    Page<Board> findAll(Pageable pageable);

    //게시판 조회순 (메인 홈페이지)
    @Query( "select b " +
            "FROM Board as b " +
            "ORDER BY b.view desc ")
    List<Board> findAllByOOrderByView(Board board);

    //게시판 추천순 (메인 홈페이지)
    @Query( "select b " +
            "FROM Board as b " +
            "ORDER BY size(b.likeVote) desc ")
    List<Board> findByOOrderByLikeVote(Board board);
}