package com.busan.travel.page.dto;

import com.busan.travel.page.entity.CommentBoard;
import com.busan.travel.page.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentBoardFormDto {

    @NotEmpty(message = "내용을 적어주세요.")
    private String content;

    private Member writer;

    public static CommentBoardFormDto toDto(CommentBoard commentBoard){
        CommentBoardFormDto commentBoardFormDto = new CommentBoardFormDto();
        commentBoardFormDto.setContent(commentBoard.getContent());
        commentBoardFormDto.setWriter(commentBoard.getWriter());
        return commentBoardFormDto;
    }

}
