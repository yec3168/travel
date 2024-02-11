package com.busan.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {
    private Long id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private int view; // 조회수
}
