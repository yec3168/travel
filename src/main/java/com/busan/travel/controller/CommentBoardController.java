package com.busan.travel.controller;

import com.busan.travel.DataNotFoundException;
import com.busan.travel.dto.CommentBoardFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.CommentBoard;
import com.busan.travel.entity.Member;
import com.busan.travel.service.BoardService;
import com.busan.travel.service.CommentBoardService;
import com.busan.travel.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

        return "redirect:/board/detail/"+commentBoard.getBoard().getId();
    }
    

    @GetMapping("/update/{id}")
    @ResponseBody
    public Object updateComment(@PathVariable("id") Long id){
        CommentBoardFormDto commentBoardFormDto = CommentBoardFormDto.toDto(commentBoardService.getComment(id));
        return commentBoardFormDto;
    }
}