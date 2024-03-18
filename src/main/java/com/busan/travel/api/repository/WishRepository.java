package com.busan.travel.api.repository;

import com.busan.travel.api.dto.KakaoResponseDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishRepository {

    private List<KakaoResponseDto> wishList = new ArrayList<>();

    public void  addWishList(KakaoResponseDto kakaoResponseDto){
        wishList.add(kakaoResponseDto);
    }
    public List<KakaoResponseDto> findAll(){
        return wishList;
    }
}
