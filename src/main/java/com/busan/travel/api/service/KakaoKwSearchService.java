package com.busan.travel.api.service;

import com.busan.travel.api.dto.KakaoResponseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class KakaoKwSearchService {

    private String BASE_URL = "https://dapi.kakao.com//v2/local/search/keyword.json";

    @Value("${kakao-rest-key}")
    private String kakao_rest_key;

    private String makeUrl(String qurey, String sort, double lat, double lng){
        return BASE_URL + "?query="+qurey + "&x=" +lng +"&y=" + lat + "&sort="+sort;
    }
    private KakaoResponseDto toDto(JSONObject item){
        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();
        kakaoResponseDto.setPlace_name((String) item.get("place_name"));
        kakaoResponseDto.setAddress_name((String) item.get("address_name"));
        kakaoResponseDto.setRoad_address_name((String) item.get("road_address_name"));
        kakaoResponseDto.setCategory_group_name((String) item.get("category_group_name"));
        kakaoResponseDto.setCategory_group_code((String) item.get("category_group_code"));
        kakaoResponseDto.setDistance((String) item.get("distance"));
        kakaoResponseDto.setPhone((String) item.get("phone"));
        kakaoResponseDto.setPlace_url((String) item.get("place_url"));
        kakaoResponseDto.setX((String) item.get("x"));
        kakaoResponseDto.setY((String) item.get("y"));
        kakaoResponseDto.setId((String) item.get("id"));

        return  kakaoResponseDto;
    }

    /* responseEntity 만들기 */
    public ResponseEntity<String> getResponse(String baseUrl) throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        // url 객체 생성
        URL url = new URL(baseUrl);
        System.out.println(url.toString());

        // header setting
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" ,"KakaoAK "+kakao_rest_key);
        headers.set("Accept", "application/json");
        headers.set("charset", "UTF-8");
        
        //Entity setting
        HttpEntity entity = new HttpEntity(headers);
        // restTemplate를 이용하여 요청을 보내고 String 형태로 받음.
        ResponseEntity<String> response =restTemplate.exchange(url.toURI(), HttpMethod.GET, entity, String.class);

        return response;
    }
    public List<KakaoResponseDto> getList(String qurey, String sort, double lat, double lng) throws IOException, URISyntaxException {
        // 한글로 들어오게 된다면 encoding 진행.
        qurey = URLEncoder.encode(qurey, "UTF-8");
        String makeURl = makeUrl(qurey, sort, lat, lng);

        //System.out.println(makeURl);
        ResponseEntity<String> response = getResponse(makeURl);
        //System.out.println(response.getBody());
        try{
            List<KakaoResponseDto> arrayList = new ArrayList<>();
            JSONParser parser = new JSONParser();

            JSONObject jsonObject =(JSONObject)parser.parse(response.getBody());
            JSONArray jsonItemList = (JSONArray) jsonObject.get("documents");
            for (Object o : jsonItemList){
                JSONObject item = (JSONObject) o;
                arrayList.add(toDto(item));
            }
            return arrayList;
        }catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
