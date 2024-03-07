package com.busan.travel.service;

import com.busan.travel.DataNotFoundException;
import com.busan.travel.dto.CommentBoardFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.CommentBoard;
import com.busan.travel.entity.Member;
import com.busan.travel.repository.CommentBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentBoardService {

    @Autowired
    private CommentBoardRepository commentBoardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    public CommentBoard create(CommentBoardFormDto commentBoardFormDto, Member writer, Board board){
        CommentBoard commentBoard =CommentBoard.createComment(commentBoardFormDto, writer, board);
        commentBoardRepository.save(commentBoard);
        return commentBoard;
    }

    public CommentBoard getComment(Long id){
        Optional<CommentBoard> op = commentBoardRepository.findById(id);
        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("Comment not Found");
    }
}