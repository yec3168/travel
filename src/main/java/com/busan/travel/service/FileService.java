package com.busan.travel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    public void makeFile(String url){
        File file = new File(url);
        if(!file.exists()){
            file.mkdir();
            System.out.println("파일을 생성하였습니다.");
        }
    }


    public static void saveFile(MultipartFile multipartFile, String url) throws Exception{
        FileOutputStream fos = new FileOutputStream(url);
        fos.write(multipartFile.getBytes());
        fos.close();
    }

    public String uuidFileName(MultipartFile multipartFile){
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString() + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));

        return filename;
    }


}
