package com.busan.travel.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
public class GoodRestaurantDto {

    private String title; //음식 이름

    private String region; // 구( 강서구, 연제구 등)

    private String addr1; // 주소

    private double lat; // 위도

    private double lng; //경도

    private String phone; // 전화번호

    private String link; // 홈페이지

    private String time; // 운영시간

    private String main_img_url; // 메인이미지 url

    private String thumb_img_url; // 썸네일 url


}
