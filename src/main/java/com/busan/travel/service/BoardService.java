package com.busan.travel.service;

import com.busan.travel.DataNotFoundException;
import com.busan.travel.dto.BoardFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.Member;
import com.busan.travel.repository.BoardRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private FileService fileService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberService memberService;

    @Value("${imgSave.location}")
    private String uploadImage; //file:///C:/travel/


    public Board save(Member user, BoardFormDto boardFormDto){
        if(!boardFormDto.getNoticeYn())
            boardFormDto.setNoticeYn(false);
        else
            boardFormDto.setNoticeYn(true);
        Board board = Board.createBoard(boardFormDto, user);
        boardRepository.save(board);

        return board;
    }
    public void saveFile(Board board, MultipartFile multipartFile){
         //file create
        String makeFolder = uploadImage + "/board/";
        fileService.makeFile(makeFolder);
        String fileName=null;
        String url =null;
        if (!multipartFile.isEmpty()){
            fileName = fileService.uuidFileName(multipartFile);
            String uploadFile = makeFolder+fileName; // 저장할 파일 위치
            url = "/image/board/"+fileName;
            try {
                fileService.saveFile(multipartFile, uploadFile);
            }catch (Exception e){
                throw new IllegalStateException("파일을 생성할 수 없습니다.");
            }
        }
        board.updateFile(fileName, url);
        boardRepository.save(board);
    }

    public void updateBoard(Board board){
        boardRepository.save(board);
    }
    public void deleteBoard(Board board){
        boardRepository.delete(board);
    }

    public Board getBoard(Long id){
        Optional<Board> op = boardRepository.findById(id);
        if(op.isPresent())
            return op.get();
        else
            throw new  DataNotFoundException("작성한 게시물을 찾을 수 없습니다.");
    }

    public Page<Board> getListNoticeTrue(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        Page<Board> noticeTrue = boardRepository.findByNoticeYnFalseOrderByCreateDateDesc(pageable);
        return noticeTrue;
    }

    public List<Board> getListNoticeFalse(int page){;
        List<Board> noticeFalse = boardRepository.findByNoticeYnTrueOrderByCreateDateDesc();
        return noticeFalse;
    }
    // 좋아요
    public void likeVote(Board board, Member member, boolean value){
        if(value){
            board.getLikeVote().add(member);
            updateBoard(board);
        }
        else{
            board.getLikeVote().remove(member);
            updateBoard(board);
        }

    }
    //싫어요
    public void hateVote(Board board, Member member, boolean value){
        if(value){ // true
            board.getHateVote().add(member);
            updateBoard(board);
        }else{
            board.getHateVote().remove(member);
            updateBoard(board);
        }

    }

    /* 추천순, 조회수 정렬*/
    public List<Board> getListMain(String sort){
        List<Board> boardList = boardRepository.findAll();
        Pageable pageable;
        List<Sort.Order> sorts = new ArrayList<>();
        //현재시간을 구해서 일주일 게시글 들만 가져옴.
        
        if(sort.equals("view")||sort.equals("")){
            pageable = PageRequest.of(0, 10);
            return boardRepository.findAllByOrderByView(pageable);
        }
        else{
            pageable = PageRequest.of(0, 10);
            return boardRepository.findAllByOrderByLikeVote(pageable);
        }
    }

    //day : 1, 7, 30
    // day전의 시간을 구해서 그만큼 더함.
    // https://sunghs.tistory.com/128
    public LocalDateTime getTime(int day){
        LocalDateTime current = LocalDateTime.now();
        return current;
    }
    
    /* 조회수 증가*/
    public void viewCountUp(Board board){
        board.upCountView(board.getView());
        boardRepository.save(board);
    }
    public void viewCountValidation(Board board, HttpServletRequest request,
                                    HttpServletResponse response){
        Cookie[] cookies = request.getCookies(); // 쿠키 얻어오기.
        /* 초기화 */
        Cookie cookie =null;
        boolean isCookie = false;

        /* request에 있는 쿠키들이 있을때 */
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            // postView 쿠키가 있을 때
            if (cookies[i].getName().equals("boardView")) {
                // cookie 변수에 저장
                cookie = cookies[i];
                // 만약 cookie 값에 현재 게시글 번호가 없을 때
                if (!cookie.getValue().contains("[" + board.getId() + "]")) {
                    // 해당 게시글 조회수를 증가시키고, 쿠키 값에 해당 게시글 번호를 추가
                    viewCountUp(board);
                    cookie.setValue(cookie.getValue() + "[" + board.getId() + "]");
                }
                isCookie = true;
                break;
            }
        }
        // 만약 postView라는 쿠키가 없으면 처음 접속한 것이므로 새로 생성
        if (!isCookie) {
            viewCountUp(board);
            cookie = new Cookie("boardView", "[" + board.getId() + "]"); // oldCookie에 새 쿠키 생성
        }

        // 쿠키 유지시간을 오늘 하루 자정까지로 설정
        long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
        long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        cookie.setPath("/"); // 모든 경로에서 접근 가능
        cookie.setMaxAge((int) (todayEndSecond - currentSecond));
        response.addCookie(cookie);
    }
}
