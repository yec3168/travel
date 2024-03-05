package com.busan.travel.controller;

import com.busan.travel.dto.BoardFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.Member;
import com.busan.travel.service.BoardService;
import com.busan.travel.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String getBoardWrite(Model model, Principal principal, BoardFormDto boardFormDto) {
        if (principal == null) {
            model.addAttribute("msg", "로그인후 이용해 주세요.");
            return "user/UserLogin";
        }
        model.addAttribute("boardFormDto", boardFormDto);
        return "board/Write";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String postBoardWrite(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                                 @RequestParam("boardFile") MultipartFile multipartFile, Model model,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/Write";
        }
        try {
            Member writer = memberService.getUserByEmail(principal.getName());
            Board board = boardService.save(writer, boardFormDto);
            if (board != null){
                boardService.saveFile(board, multipartFile);
                return "redirect:/board/list";
            }
        } catch (Exception e) {
            model.addAttribute("errorMsg", "게시글을 등록하지 못하였습니다.");
        }
        return "board/Write";
    }

    @GetMapping("/list")
    public String getBoardList(Model model, @RequestParam(value = "page", defaultValue = "0")int page) {
        Page<Board> paging = boardService.getList(page);
        model.addAttribute("paging", paging);
        return "board/List";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(@PathVariable("id")Long id, Model model,
                              HttpServletResponse response,
                              HttpServletRequest request){
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        boardService.viewCountValidation(board, request, response);
        return "board/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String  boardUpdate(@PathVariable("id")Long id, Model model,
                               Principal principal){
        Board board = boardService.getBoard(id);
        if(!board.getWriter().getEmail().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");

        BoardFormDto boardFormDto =  BoardFormDto.toDto(board);
        model.addAttribute("boardFormDto", boardFormDto);
        return "board/Write";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String  boardUpdate(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                               @PathVariable("id")Long id, Principal principal,
                               Model model, @RequestParam("boardFile") MultipartFile multipartFile){
        if(bindingResult.hasErrors()){
            return "board/Write";
        }
        Board board = boardService.getBoard(id);
        if(!board.getWriter().getEmail().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");

        if(!multipartFile.isEmpty())
            boardService.saveFile(board, multipartFile);

        board.update(boardFormDto.getSubject(), boardFormDto.getContent());
        boardService.updateBoard(board);
        return "redirect:/board/detail/"+id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable("id")Long id, Principal principal){
        Board board = boardService.getBoard(id);
        if(!board.getWriter().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        boardService.deleteBoard(board);
        return "redirect:/board/list";
    }
}
