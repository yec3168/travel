package com.busan.travel.api.entity;

import com.busan.travel.page.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place_name;// 장소이름

    private String address_name; // 주소

    private String road_address_name; // 도로명 주소

    private String category_group_name; // 그룹명(음식점, 편의점, 노래방 등)

    private String category_group_code; // 그룹코드

    private String distance; // 거리

    private String phone ; //전화번호

    private String place_url; // 홈페이지

    private String x; //위도

    private String y; //경도

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자

    @Builder
    public Wish(String place_name, String  address_name, String road_address_name,
                 String category_group_name, String category_group_code, String distance,
                 String phone, String place_url, String x, String y, Member member){
        this.place_name = place_name;
        this.address_name = address_name;
        this.road_address_name = road_address_name;
        this.category_group_name = category_group_name;
        this.category_group_code = category_group_code;
        this.distance = distance;
        this.phone = phone;
        this.place_url = place_url;
        this.x = x;
        this.y = y;
        this.member = member;
    }
}
