package com.busan.travel.page.repository;

import com.busan.travel.page.entity.Board;
import com.busan.travel.page.entity.CommentBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentBoardRepository extends JpaRepository<CommentBoard, Long> {
    Optional<CommentBoard> findById(Long id);
    Page<CommentBoard> findAllByBoard(Board board, Pageable pageable); // 페이지 정렬

    @Query("SELECT c "
            + "FROM CommentBoard as c "
            + "WHERE c.board = :board "
            + "ORDER BY SIZE(c.vote) DESC, c.createDate")
    // 정렬 1순위. 추천순, 2순위 생성 날짜순.
    Page<CommentBoard> findAllByBoardOrderByVote(Board board, Pageable pageable);
}
