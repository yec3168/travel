package com.busan.travel.repository;

import com.busan.travel.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);

    @Query(" SELECT b "
            + "FROM Board as b "
            + "WHERE b.noticeYn = true "
            + "order by b.createDate desc  "
    )
    Page<Board> findByNoticeYnTrueOrderByCreateDateDesc(Pageable pageable);


    @Query(  "SELECT b "
            + "from Board as b "
            + "WHERE b.noticeYn =false "
            + "order by b.createDate desc ")
    Page<Board> findByNoticeYnFalseOrderByCreateDateDesc(Pageable pageable);

    //    @Query(" SELECT b "
//            + "FROM Board as b "
//            + "WHERE b.noticeYn = true "
//            + "order by b.createDate desc  union "
//            + "SELECT b "
//            + "from Board as b "
//            + "WHERE b.noticeYn =false "
//            + "order by b.createDate desc "
//    )
//    Page<Board> findAll(Pageable pageable);
    Page<Board> findAll(Pageable pageable);
}
