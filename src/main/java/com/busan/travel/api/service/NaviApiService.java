package com.busan.travel.api.service;

import com.busan.travel.api.dto.LinePathDto;
import com.busan.travel.api.dto.NaviApiResponseDto;
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
    private String BASE_URL = "https://apis-navi.kakaomobility.com/v1/directions";

    public String makeURL(String origin, String originX, String originY,
                          String destination, String destinationX, String destinationY) throws UnsupportedEncodingException {
        return BASE_URL + "?origin="+URLEncoder.encode(originX, "UTF-8")+","+URLEncoder.encode(originY, "UTF-8")+
                "&destination="+URLEncoder.encode(destinationX, "UTF-8")+","+URLEncoder.encode(destinationY, "UTF-8");
    }

    private NaviApiResponseDto todto(JSONObject item){
        System.out.println(item);
        System.out.println();
        NaviApiResponseDto naviApiResponseDto = new NaviApiResponseDto();
        naviApiResponseDto.setPlace_name((String) item.get("name"));
        naviApiResponseDto.setAddress_name((String) item.get("address_name"));
        naviApiResponseDto.setRoad_address_name((String) item.get("road_address_name"));
        naviApiResponseDto.setX((String) item.get("x"));
        naviApiResponseDto.setY((String) item.get("y"));

        return naviApiResponseDto;
    }

    private LinePathDto toDto(JSONObject item){
        LinePathDto linePathDto = new LinePathDto();

        return linePathDto;
    }

    public List<NaviApiResponseDto> getPlace(String origin, String originX, String originY,
                                             String destination, String destinationX, String destinationY) throws UnsupportedEncodingException, URISyntaxException, ParseException {
        // 1. URI 생성.
        URI uri = new URI(makeURL(origin, originX, originY, destination, destinationX, destinationY));
        System.out.println(uri.toString());

        //2. header 생성.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" ,"KakaoAK "+kakao_rest_key);
        headers.set("Accept", "application/json");
        headers.set("charset", "UTF-8");
        
        
        //3. Request 요청
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response =new RestTemplate().exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());

        // 4. json형태를 dto 형태로 전달.
        JSONParser parser = new JSONParser();
        JSONObject jsonObject =(JSONObject)parser.parse(response.getBody());
        System.out.println(jsonObject.toString());
        JSONArray routes = (JSONArray) jsonObject.get("routes");
        JSONObject route0 = (JSONObject)routes.get(0);
        JSONArray sections = (JSONArray)route0.get("sections");
        JSONObject section0 = (JSONObject) sections.get(0);
        JSONArray roads = (JSONArray)section0.get("roads");

//        System.out.println(roads.size());
//        System.out.println(roads.toString());
        List<JSONObject> roadList = new ArrayList<>();
        for(Object o : roads){
            JSONObject item = (JSONObject)o;
            JSONArray vertexes = (JSONArray) item.get("vertexes");
            System.out.println(vertexes.toString());
        }

        List<NaviApiResponseDto> result = new ArrayList<>();

//        for(Object o : JSONArray){
//            result.add(todto((JSONObject) o));
//        }
        return result;
    }
}
