package com.busan.travel.repository;

import com.busan.travel.entity.CommentBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentBoardRepository extends JpaRepository<CommentBoard, Long> {
    Optional<CommentBoard> findById(Long id);
    Page<CommentBoard> findAll(Pageable pageable);
}
