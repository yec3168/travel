package com.busan.travel.page.service;

import com.busan.travel.page.dto.ReviewFormDto;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import com.busan.travel.page.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewService {
    @Value("${imgSave.location}")
    private String uploadImage; //file:///C:/travel/
    @Autowired
    private FileService fileService;
    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(ReviewFormDto reviewFormDto, Member writer,
                             MultipartFile multipartFile){
        //파일저장.
        saveFile(multipartFile, reviewFormDto);

        Review review = Review.builder()
                .subject(reviewFormDto.getSubject())
                .content(reviewFormDto.getContent())
                .stars(reviewFormDto.getStars())
                .writer(writer)
                .thumbnail(reviewFormDto.getThumbnail())
                .url(reviewFormDto.getUrl())
                .build();
        reviewRepository.save(review);
    }

    public void saveFile(MultipartFile multipartFile, ReviewFormDto reviewFormDto){
        //폴더 생성.
        String makeFolder = uploadImage + "/review/";
        fileService.makeFile(makeFolder);

        //파일 이름, url 생성.
        String fileName=null;
        String url =null;
        if (!multipartFile.isEmpty()){
            // uuid 이름 생성.
            fileName = fileService.uuidFileName(multipartFile);
            String uploadFile = makeFolder+fileName; // 저장할 파일 위치
            url = "/image/review/"+fileName;
            try {
                fileService.saveFile(multipartFile, uploadFile);
            }catch (Exception e){
                throw new IllegalStateException("파일을 생성할 수 없습니다.");
            }
        }
        reviewFormDto.setThumbnail(fileName);
        reviewFormDto.setUrl(url);
    }

}
