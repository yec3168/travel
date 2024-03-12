package com.busan.travel.page.service;

import com.busan.travel.DataNotFoundException;
import com.busan.travel.page.dto.CommentBoardFormDto;
import com.busan.travel.page.entity.Board;
import com.busan.travel.page.entity.CommentBoard;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.repository.CommentBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public void updateComment(String content, CommentBoard commentBoard){
        commentBoard.updateContent(content);
        commentBoardRepository.save(commentBoard);
    }

    public void deleteComment(CommentBoard commentBoard){
        commentBoardRepository.delete(commentBoard);
    }

    public CommentBoard getComment(Long id){
        Optional<CommentBoard> op = commentBoardRepository.findById(id);
        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("Comment not Found");
    }

    public Page<CommentBoard> getCommentList(Board board, int page, String sort){
        Pageable pageable;
        // 최신순
        if(sort.equals("createDate")|| sort.equals("")){
            List<Sort.Order> sorts = new ArrayList<>();
            sorts.add(Sort.Order.desc("createDate"));
            pageable = PageRequest.of(page, 10, Sort.by(sorts));
            return commentBoardRepository.findAllByBoard(board, pageable);
        }else{
            pageable = PageRequest.of(page, 10);
            return commentBoardRepository.findAllByBoardOrderByVote(board, pageable);
        }

    }


    public void vote(CommentBoard commentBoard, Member member, boolean state){
        if(state){
            commentBoard.getVote().add(member);
            commentBoardRepository.save(commentBoard);
        }
        else{
            commentBoard.getVote().remove(member);
            commentBoardRepository.save(commentBoard);
        }
    }
}
