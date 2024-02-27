package com.busan.travel.dto;

import com.busan.travel.entity.Board;
import com.busan.travel.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardFormDto {
    private Long id;

    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;

    private Member writer;

    private Boolean noticeYn;

    private String fileName;

    private String url;


    public static BoardFormDto toDto(Board board){
        BoardFormDto boardFormDto = new BoardFormDto();
        boardFormDto.setId(board.getId());
        boardFormDto.setSubject(board.getSubject());
        boardFormDto.setContent(board.getContent());
        boardFormDto.setWriter(board.getWriter());
        boardFormDto.setNoticeYn(board.getNoticeYn());
        boardFormDto.setFileName(board.getFileName());
        boardFormDto.setUrl(board.getUrl());

        return boardFormDto;
    }
}
