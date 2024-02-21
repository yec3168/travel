package com.busan.travel.entity;

import com.busan.travel.dto.BoardFormDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String subject;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    private int view; // 조회수

    private Boolean noticeYn;

    private String fileName;

    private String url;

    @Builder
    public Board(String subject, String content, int view,
                 User writer, Boolean  noticeYn, String fileName,
                 String url){
        this.subject = subject;
        this.content =content;
        this.view = view;
        this.writer = writer;
        this.noticeYn = noticeYn;
        this.fileName = fileName;
        this.url =url;
    }

    public static Board createBoard(BoardFormDto boardFormDto, User writer){
        Board board = Board.builder()
                .subject(boardFormDto.getSubject())
                .content(boardFormDto.getContent())
                .writer(writer)
                .noticeYn(boardFormDto.getNoticeYn())
                .build();

        return board;
    }

    public void updateFile(String fileName, String url){
        this.fileName = fileName;
        this.url = url;
    }
}
