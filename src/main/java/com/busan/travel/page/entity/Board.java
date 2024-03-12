package com.busan.travel.page.entity;

import com.busan.travel.page.dto.BoardFormDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
    private Member writer;

    private int view; // 조회수

    @ColumnDefault("false")
    private Boolean noticeYn;

    private String fileName;

    private String url;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Member> likeVote;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Member> hateVote;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentBoard> commentBoardList = new ArrayList<>();
    @Builder
    public Board(String subject, String content, int view,
                 Member writer, Boolean  noticeYn, String fileName,
                 String url){
        this.subject = subject;
        this.content =content;
        this.view = view;
        this.writer = writer;
        this.noticeYn = noticeYn;
        this.fileName = fileName;
        this.url =url;
    }

    public static Board createBoard(BoardFormDto boardFormDto, Member writer){
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
    public void update(String subject, String content){
        this.subject = subject;
        this.content = content;
    }
    public void upCountView(int view){
        this.view =view+1;
    }

}
