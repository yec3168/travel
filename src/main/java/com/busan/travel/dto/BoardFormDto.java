package com.busan.travel.dto;

import com.busan.travel.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardFormDto {
    private Long id;

    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;

    private User writer;

    private Boolean noticeYn;

    private String fileName;

    private String url;
}
