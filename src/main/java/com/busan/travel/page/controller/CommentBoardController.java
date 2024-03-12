package com.busan.travel.page.controller;

import com.busan.travel.page.dto.CommentBoardFormDto;
import com.busan.travel.page.dto.CommentUpdateForm;
import com.busan.travel.page.entity.Board;
import com.busan.travel.page.entity.CommentBoard;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.service.CommentBoardService;
import com.busan.travel.page.service.BoardService;
import com.busan.travel.page.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/comment")
public class CommentBoardController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentBoardService commentBoardService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(@Valid CommentBoardFormDto commentBoardFormDto,
                                BindingResult bindingResult, @PathVariable("id")Long id,
                                Principal principal, Model model){
        Board board = boardService.getBoard(id);
        Member writer = memberService.getUserByEmail(principal.getName());

        if(bindingResult.hasErrors())
            return "board/detail";
        CommentBoard commentBoard =commentBoardService.create(commentBoardFormDto, writer, board);

        if(!commentBoard.getWriter().getEmail().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 권한이 없습니다./n 로그인후 이용해주세요.");
        return "redirect:/board/detail/"+commentBoard.getBoard().getId();
    }
    

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    @ResponseBody
    public Object updateComment(@PathVariable("id") Long id){
        CommentBoard commentBoard = commentBoardService.getComment(id);

        CommentUpdateForm commentUpdateForm = new CommentUpdateForm();
        commentUpdateForm.setContent(commentBoard.getContent());
        commentUpdateForm.setNickName(commentBoard.getWriter().getNickName());
        return commentUpdateForm;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String updateComment(@RequestParam("updateContent")String content, @PathVariable("id")Long id,
                                Principal principal, Model model){
        CommentBoard commentBoard = commentBoardService.getComment(id);

        if(!commentBoard.getWriter().getEmail().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");

        // 업데이트 부분.
        commentBoardService.updateComment(content, commentBoard);

        return "redirect:/board/detail/"+commentBoard.getBoard().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id")Long id, Principal principal){
        CommentBoard commentBoard = commentBoardService.getComment(id);

        if(!commentBoard.getWriter().getEmail().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");

        commentBoardService.deleteComment(commentBoard);

        return "redirect:/board/detail/"+commentBoard.getBoard().getId();
    }


    @PreAuthorize("isAuthenticated")
    @GetMapping("/like/{id}")
    public String likeComment(@PathVariable("id")Long id, Principal principal){
        CommentBoard commentBoard = commentBoardService.getComment(id);
        Member member = memberService.getUserByEmail(principal.getName());
        if(commentBoard.getVote().contains(member))
            commentBoardService.vote(commentBoard, member, false);
        else
            commentBoardService.vote(commentBoard, member, true);
        return "redirect:/board/detail/"+commentBoard.getBoard().getId();
    }
}
