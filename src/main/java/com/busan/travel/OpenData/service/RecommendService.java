package com.busan.travel.OpenData.service;

import com.busan.travel.OpenData.dto.RecommendResponseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class RecommendService {

    @Value("${recommend-key}")
    private String recommend_place_key;
    private RecommendResponseDto toDto(JSONObject item){
        RecommendResponseDto recommendResponseDto =new RecommendResponseDto();
        recommendResponseDto.setMain_title((String) item.get("MAIN_ITEM"));
        recommendResponseDto.setGugun_nm((String) item.get("GUGUN_NM"));
        recommendResponseDto.setCate2_num((String) item.get("CATE2_NM"));
        recommendResponseDto.setLat((double) item.get("LAT"));
        recommendResponseDto.setLng((double) item.get("LNG"));
        recommendResponseDto.setPlace((String) item.get("PLACE"));
        recommendResponseDto.setTitle((String) item.get("TITLE"));
        recommendResponseDto.setSubTitle((String) item.get("SUB_TITLE"));
        recommendResponseDto.setAddr1((String) item.get("ADDR1"));
        recommendResponseDto.setAddr2((String) item.get("ADDR2"));
        recommendResponseDto.setCntct_tel((String) item.get("CNTCT_TEL"));
        recommendResponseDto.setHomepage_url((String) item.get("HOMEPAGE_URL"));
        recommendResponseDto.setMain_img_normal((String) item.get("MAIN_IMG_NORMAL"));
        recommendResponseDto.setMain_img_thumb((String) item.get("MAIN_IMG_THUMB"));
        recommendResponseDto.setItemCntnts((String) item.get("ITEMCNTNTS"));
        return recommendResponseDto;
    }
    public StringBuilder makeURI( ) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/RecommendedService/getRecommendedKr"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+recommend_place_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*JSON방식으로 호출 시 파라미터 resultType=json 입력*/
        return urlBuilder;
    }

    public List<RecommendResponseDto> getPlace() throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
        // URL 설정
        System.out.println(makeURI().toString());
        URL url = new URL(makeURI().toString());

        // request
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<String> response = new RestTemplate().exchange(url.toURI(), HttpMethod.GET, entity, String.class);


        //JSON
        JSONParser parser = new JSONParser();
        try {
            JSONObject body = (JSONObject) parser.parse(response.getBody());
            JSONObject recommendedKr = (JSONObject) body.get("getRecommendedKr");
            JSONArray items = (JSONArray)recommendedKr.get("item");
            List<RecommendResponseDto> result = new ArrayList<>();

            for(Object o : items)
                result.add(toDto((JSONObject) o));

            return result;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
