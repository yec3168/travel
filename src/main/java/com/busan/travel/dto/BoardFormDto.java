package com.busan.travel.dto;

import com.busan.travel.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardFormDto {
    private Long id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private User writer;

    private int view; // 조회수

    private boolean noticeYn;
}
