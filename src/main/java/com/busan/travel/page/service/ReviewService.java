package com.busan.travel.page.service;

import com.busan.travel.page.dto.ReviewFormDto;
import com.busan.travel.page.entity.Board;
import com.busan.travel.page.entity.CommentReview;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.entity.Review;
import com.busan.travel.page.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Value("${imgSave.location}")
    private String uploadImage; //file:///C:/travel/
    @Autowired
    private FileService fileService;
    @Autowired
    private ReviewRepository reviewRepository;

    public Review findReview(Long id){
        Optional<Review> op = reviewRepository.findById(id);
        return op.orElse(null);
    }
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
        String fileName=reviewFormDto.getThumbnail();
        String url =reviewFormDto.getUrl();
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

    public Page<Review> findAllByCreateDate(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return reviewRepository.findByOrderByCreateDateDesc(pageable);
    }


    // 상세정보 오른쪽 최신 생성글
    public List<Review> recently_review(){
        return  reviewRepository.findByOrderByCreateDate();
    }


    public ReviewFormDto changeDto(Review review){
        return ReviewFormDto.toDto(review);
    }

    // update Review
    public void updateReview( Review review, ReviewFormDto reviewFormDto,
                             MultipartFile multipartFile){
        if(multipartFile != null){
            saveFile(multipartFile, reviewFormDto);
        }
        review.updateContent(reviewFormDto);
        reviewRepository.save(review);
    }

    // delete Review
    public void delete(Review review){
        reviewRepository.delete(review);
    }


    //검색기능
    public Page<Review> searchKeyword(int page, String keyword, String searchType) throws UnsupportedEncodingException {
        Page<Review> result;
        Pageable pageable = PageRequest.of(page, 10);

        System.out.println(keyword);
        result = switch (searchType) {
            case "writer" ->
                //작성자. (닉네임으로 검색.)
                    reviewRepository.findByNickname(keyword, pageable);
            case "subject" ->
                //제목
                    reviewRepository.findBySubjectContaining(keyword, pageable);
            case "content" ->
                //내용
                    reviewRepository.findByContentContaining(keyword, pageable);
            default ->
                // 전체검색.
                    reviewRepository.findAllBySubjectOrContentOrWriterContaining(keyword, pageable);
        };
        return  result;
    }

}
