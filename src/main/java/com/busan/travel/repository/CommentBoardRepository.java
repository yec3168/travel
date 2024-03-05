package com.busan.travel.repository;

import com.busan.travel.entity.CommentBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentBoardRepository extends JpaRepository<CommentBoard, Long> {
    Page<CommentBoard> findAll(Pageable pageable);
}
