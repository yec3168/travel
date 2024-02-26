package com.busan.travel.controller;

import com.busan.travel.dto.BoardFormDto;
import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.User;
import com.busan.travel.repository.BoardRepository;
import com.busan.travel.service.BoardService;
import com.busan.travel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

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
            User writer = userService.getUserByEmail(principal.getName());
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

}
