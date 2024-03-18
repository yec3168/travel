package com.busan.travel.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaviApiResponseDto {

    private String place_name;// 장소이름

    private String address_name; // 주소

    private String road_address_name; // 도로명 주소

    private String x;

    private String y;
}
