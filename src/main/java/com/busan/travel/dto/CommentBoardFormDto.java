package com.busan.travel.dto;

import com.busan.travel.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentBoardFormDto {

    @NotEmpty(message = "내용을 적어주세요.")
    private String content;

    private Member writer;

}
