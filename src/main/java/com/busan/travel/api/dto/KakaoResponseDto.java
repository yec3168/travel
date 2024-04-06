package com.busan.travel.api.dto;

import com.busan.travel.api.entity.Wish;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoResponseDto {
    private String  uid;

    private String place_name;// 장소이름

    private String address_name; // 주소

    private String road_address_name; // 도로명 주소

    private String category_group_name; // 그룹명(음식점, 편의점, 노래방 등

    private String category_group_code; // 그룹코드

    private String distance; // 거리

    private String phone ; //전화번호

    private String place_url; // 홈페이지

    private String x;

    private String y;


    public static KakaoResponseDto toDto(Wish wish){
        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();
        kakaoResponseDto.setUid(wish.getUid());
        kakaoResponseDto.setPlace_name(wish.getPlace_name());
        kakaoResponseDto.setAddress_name(wish.getAddress_name());
        kakaoResponseDto.setRoad_address_name(wish.getRoad_address_name());
        kakaoResponseDto.setCategory_group_name(wish.getCategory_group_name());
        kakaoResponseDto.setCategory_group_code(wish.getCategory_group_code());
        kakaoResponseDto.setDistance(wish.getDistance());
        kakaoResponseDto.setPhone(wish.getPhone());
        kakaoResponseDto.setPlace_url(wish.getPlace_url());
        kakaoResponseDto.setX(wish.getX());
        kakaoResponseDto.setY(wish.getY());

        return  kakaoResponseDto;
    }

}
