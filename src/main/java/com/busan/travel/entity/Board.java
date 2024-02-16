package com.busan.travel.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private int view; // 조회수

    private boolean notice_yn;
}
