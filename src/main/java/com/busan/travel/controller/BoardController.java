package com.busan.travel.controller;

import com.busan.travel.dto.BoardFormDto;
import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.User;
import com.busan.travel.service.BoardService;
import com.busan.travel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/list")
    public String getBoardList(){
        return "board/List";
    }

    @GetMapping("/write")
    public String getBoardWrite(Model model, Principal principal){

        if(principal == null){
            model.addAttribute("msg", "로그인후 이용해 주세요.");
            return "user/UserLogin";
        }
        Optional<User> op =userService.getUser(principal.getName());
        if(op.isPresent()){
            User user = op.get();

            model.addAttribute("user", user);
        }
        return "board/Write";

    }

    @PostMapping("/write")
    public String postBoardWirte(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                                 User user, @RequestParam("boardFile") MultipartFile multipartFile,
                                 Model model){
        if(bindingResult.hasErrors())
            return "board/Write";

        try{
            Board board = boardService.savePost(user, boardFormDto, multipartFile);
            if(board ==null){
                model.addAttribute("errorMsg", "게시글을 생성할 수 없습니다.");
            }
        }catch (Exception e){
            model.addAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/board/list";
    }
}
