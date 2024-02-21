package com.busan.travel.service;

import com.busan.travel.dto.BoardFormDto;
import com.busan.travel.entity.Board;
import com.busan.travel.entity.User;
import com.busan.travel.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private FileService fileService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;

    @Value("${imgSave.location}")
    private String uploadImage; //file:///C:/travel/


    public Board save(User user, BoardFormDto boardFormDto){

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



}
