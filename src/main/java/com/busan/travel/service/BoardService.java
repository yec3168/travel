package com.busan.travel.service;

import com.busan.travel.DataNotFoundException;
import com.busan.travel.dto.BoardFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.Member;
import com.busan.travel.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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
        if(boardFormDto.getNoticeYn())
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

    public Page<Board> getList(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findAll(pageable);
    }

}
