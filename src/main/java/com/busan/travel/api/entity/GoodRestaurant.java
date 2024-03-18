package com.busan.travel.api.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GoodRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    private String title; //음식 이름

    private String region; // 구( 강서구, 연제구 등)

    private String addr1; // 주소

    private double lat; // 위도

    private double lng; //경도

    private String phone; // 전화번호

    private String link; // 홈페이지

    private String operate_time; // 운영시간

    private String main_img_url; // 메인이미지 url

    private String thumb_img_url; // 썸네일 url


    @Builder
    public GoodRestaurant(String title, String region, String addr1,
                          double lat, double lng, String phone,
                          String link, String operate_time, String main_img_url,
                          String thumb_img_url){
        this.title =title;
        this.region =region;
        this.addr1 = addr1;
        this.lat = lat;
        this.lng = lng;
        this.phone =phone;
        this.link = link;
        this.operate_time =operate_time;
        this.main_img_url =main_img_url;
        this.thumb_img_url = thumb_img_url;
    }
}
