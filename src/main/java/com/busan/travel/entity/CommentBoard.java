package com.busan.travel.entity;

import com.busan.travel.dto.CommentBoardFormDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class CommentBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "command_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER) // detail의 openPop때문에 eager로 바꿈
    @JoinColumn(name = "user_id")
    private Member writer;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
            ;
    @ManyToMany
    private Set<Member> vote;

    @Builder
    public CommentBoard(String content, Member writer, Board board){
        this.content = content;
        this.writer =writer;
        this.board = board;
    }

    public static CommentBoard createComment(CommentBoardFormDto commentBoardFormDto,
                                             Member writer, Board board){
        CommentBoard commentBoard = CommentBoard.builder()
                                    .content(commentBoardFormDto.getContent())
                                    .writer(writer)
                                    .board(board)
                                    .build();

        return  commentBoard;
    }

    public void updateContent(String content){
        this.content =content;
    }

}
