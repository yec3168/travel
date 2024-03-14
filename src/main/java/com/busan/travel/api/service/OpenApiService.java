package com.busan.travel.api.service;

import com.busan.travel.api.dto.GoodRestaurantDto;
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

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenApiService {
    private final String URL ="http://apis.data.go.kr/6260000/FoodService/getFoodKr";
    @Value("${dataset-key}")
    private String busan_dataset_key;
    private final String secretKey = "?serviceKey=";
    private final String numOfRows = "&numOfRows=5";
    private final String resultType ="&resultType=json";

    // 데이터 셋  url 구성
    private String makeUrl() throws UnsupportedEncodingException{
        return URL +
                secretKey +
                busan_dataset_key+
                numOfRows +
                resultType;
    }
    private GoodRestaurantDto makeDto(JSONObject item){
        GoodRestaurantDto goodRestaurantDto = new GoodRestaurantDto();
        goodRestaurantDto.setTitle((String) item.get("MAIN_TITLE"));
        goodRestaurantDto.setRegion((String) item.get("GUGUN_NM"));
        goodRestaurantDto.setAddr1((String) item.get("ADDR1"));
        goodRestaurantDto.setLat((double) item.get("LAT"));
        goodRestaurantDto.setLng((double) item.get("LNG"));
        goodRestaurantDto.setPhone((String) item.get("CNTCT_TEL"));
        goodRestaurantDto.setLink((String) item.get("HOMEPAGE_URL"));
        goodRestaurantDto.setTime((String) item.get("USAGE_DAY_WEEK_AND_TIME"));
        goodRestaurantDto.setMain_img_url((String) item.get("MAIN_IMG_NORMAL"));
        goodRestaurantDto.setThumb_img_url((String) item.get("MAIN_IMG_THUMB"));
        return goodRestaurantDto;
    }

    /* open api test */
    /* postman으로 요청을 보내면 정상적으로 작동하지만 아래 코드로 요청시 SERVICE ERROR SERVICE_KEY_IS_NOT_REGISTERED_ERROR 30 발생
    *  이유는 우리가 브라우저 url을 입력하게 되면 ascii문자 집합에 없는 문자를 사용해서 발생.*/
    public ResponseEntity<String> fetch() throws UnsupportedEncodingException {
        System.out.println(makeUrl());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(makeUrl(), HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        return  response;
    }
    public ResponseEntity<String> getEntity() throws UnsupportedEncodingException, URISyntaxException {
        String makeUrl = makeUrl();
        URL url = null;
        try{
            url = new URL(makeUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<String > response  = restTemplate.exchange(url.toURI(), HttpMethod.GET, entity, String.class);
        return response;
    }


    /* open API 서버로부터 데이터를 JSON 형태로 받아오기*/
    public List<GoodRestaurantDto> getJson() throws UnsupportedEncodingException, URISyntaxException {
        // json데이터를 string 형태로 받아옴.
        ResponseEntity<String> response = getEntity();

        // JSON데이터를 파싱하는 java 클래스.  객체나 배열로 변환 해줌.
        JSONParser jsonParser = new JSONParser();

        /* json 데이터 파싱 */
        try {
            // {getFoodkr : {item}, {pageNo} ...} 식으로 파싱 되는 듯.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());

            /* JSON 데이터 부분 파싱 */
            JSONObject jsonGetFoodKr = (JSONObject) jsonObject.get("getFoodKr"); // response 파싱
            JSONArray jsonItemList = (JSONArray) jsonGetFoodKr.get("item");

            List<GoodRestaurantDto> result = new ArrayList<>();

            for (Object o : jsonItemList){
                JSONObject item = (JSONObject) o;
                result.add(makeDto(item));
            }
            return result;
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
