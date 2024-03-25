package com.busan.travel.page.controller;

import com.busan.travel.page.dto.BoardFormDto;
import com.busan.travel.page.dto.CommentBoardFormDto;
import com.busan.travel.page.entity.Board;
import com.busan.travel.page.entity.CommentBoard;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.service.CommentBoardService;
import com.busan.travel.page.service.MemberService;
import com.busan.travel.page.service.BoardService;
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

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentBoardService commentBoardService;


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
    public String getBoardList(Model model,
                               @RequestParam(value = "page", defaultValue = "0")int page) {
        List<Board> pagingTrue = boardService.getListNoticeFalse(page);
        Page<Board> paging = boardService.getListNoticeTrue(page);
        model.addAttribute("paging", paging);
        model.addAttribute("pagingTrue", pagingTrue);
        return "board/List";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(@PathVariable("id")Long id, Model model,
                              HttpServletResponse response, HttpServletRequest request,
                              CommentBoardFormDto commentBoardFormDto,
                              @RequestParam(value = "page", defaultValue = "0")int page,
                              @RequestParam(value = "sort", defaultValue = "")String sort){
        Board board = boardService.getBoard(id);
        Page<CommentBoard> paging = commentBoardService.getCommentList(board, page, sort);
        model.addAttribute("commentBoardFormDto", commentBoardFormDto);
        model.addAttribute("board", board);
        model.addAttribute("paging", paging);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String likeVote(@PathVariable("id") Long id, Principal principal){
        Board board = boardService.getBoard(id);
        Member member = memberService.getUserByEmail(principal.getName());

        if(board.getLikeVote().contains(member))
            boardService.likeVote(board, member, false);
        else
            boardService.likeVote(board, member, true);
        return "redirect:/board/detail/"+id;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hate/{id}")
    public String hateVote(@PathVariable("id") Long id, Principal principal){
        Board board = boardService.getBoard(id);
        Member member = memberService.getUserByEmail(principal.getName());

        if(board.getHateVote().contains(member))
            boardService.hateVote(board, member, false);
        else
            boardService.hateVote(board, member, true);

        return "redirect:/board/detail/"+id;
    }




    //검색기능
    @GetMapping("/search")
    public String searchValue(@RequestParam(value = "keyword", defaultValue = "")String keyword,
                              @RequestParam(value = "searchType", defaultValue = "") String searchType,
                              @RequestParam(value = "page", defaultValue = "0")int page, Model model) throws UnsupportedEncodingException {
        // 공지글은 고정.
        List<Board> pagingTrue = boardService.getListNoticeFalse(page);
        model.addAttribute("pagingTrue", pagingTrue);

        //keyword에 따라 검색기능 활성화
        Page<Board> paging = boardService.searchKeyword(page, keyword, searchType);

        model.addAttribute("paging", paging);
        return "board/List";
    }
}
