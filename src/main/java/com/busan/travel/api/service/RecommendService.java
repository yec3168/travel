package com.busan.travel.api.service;

import com.busan.travel.api.dto.RecommendResponseDto;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RecommendService {
    @Value("@{recommend-place-key}")
    private String recommend_place_key;
    private final String BASE_URL = "http://apis.data.go.kr/6260000/RecommendedService";

    public StringBuilder makeURI( ) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/RecommendedService/getRecommendedKr"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+recommend_place_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*JSON방식으로 호출 시 파라미터 resultType=json 입력*/
        urlBuilder.append("&" + URLEncoder.encode("UC_SEQ","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*콘텐츠 ID*/
        return urlBuilder;
    }

    public RecommendResponseDto getPlace() throws UnsupportedEncodingException, MalformedURLException {
        // URL 설정
        URL url = new URL(makeURI().toString());


        return null;
    }
}
