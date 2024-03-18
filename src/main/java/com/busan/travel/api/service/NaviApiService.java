package com.busan.travel.api.service;

import com.busan.travel.api.dto.NaviApiResponseDto;
import jakarta.validation.constraints.Size;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
public class NaviApiService {

    @Value("${kakao-rest-key}")
    private String kakao_rest_key;
    private String BASE_URL = "https://dapi.kakao.com//v2/local/search/keyword.json";

    public String makeURL(String qurey){
        return BASE_URL + "?query="+qurey+"&size=1";
    }

    private NaviApiResponseDto todto(JSONObject item){
        NaviApiResponseDto naviApiResponseDto = new NaviApiResponseDto();
        naviApiResponseDto.setPlace_name((String) item.get("place_name"));
        naviApiResponseDto.setAddress_name((String) item.get("address_name"));
        naviApiResponseDto.setRoad_address_name((String) item.get("road_address_name"));
        naviApiResponseDto.setX((String) item.get("x"));
        naviApiResponseDto.setY((String) item.get("y"));

        return naviApiResponseDto;
    }

    public List<NaviApiResponseDto> getPlace(String qurey) throws UnsupportedEncodingException, URISyntaxException, ParseException {
        qurey = URLEncoder.encode(qurey, "UTF-8"); // utf-8 적용

        // 1. URI 생성.
        URI uri = new URI(makeURL(qurey));



        //2. header 생성.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" ,"KakaoAK "+kakao_rest_key);
        headers.set("Accept", "application/json");
        headers.set("charset", "UTF-8");
        
        
        //3. Request 요청
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response =new RestTemplate().exchange(uri, HttpMethod.GET, entity, String.class);


        // 4. json형태를 dto 형태로 전달.
        JSONParser parser = new JSONParser();
        JSONObject jsonObject =(JSONObject)parser.parse(response.getBody());
        JSONArray jsonitem = (JSONArray) jsonObject.get("documents");
        List<NaviApiResponseDto> result = new ArrayList<>();

        for(Object o : jsonitem){
            result.add(todto((JSONObject) o));
        }
        return result;
    }
}
