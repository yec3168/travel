package com.busan.travel.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class KakaoResponseDto {
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
}
