package com.busan.travel.api.controller;

import com.busan.travel.api.dto.KakaoResponseDto;
import com.busan.travel.api.service.KakaoKwSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class RegionController {

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @Autowired
    private final KakaoKwSearchService kakaoKwSearchService;

    private List<KakaoResponseDto> wishList = new ArrayList<>();

//    @GetMapping("/test")
//    public String test(Model model){
//        model.addAttribute("kakao_admin_key", kakao_admin_key);
//        return "search";
//    }

    @GetMapping("/search")
    private String kwSearch(Model model) {
        model.addAttribute("kakao_admin_key", kakao_admin_key);
        //model.addAttribute("keywordList", kakaoKwSearchService.getList(kw));
        return "region/search";
    }
    @GetMapping("/keyword")
    @ResponseBody
    private List<KakaoResponseDto> kwSearch(@RequestParam(value = "kw", defaultValue = "")String kw,
                                            @RequestParam(value = "sort", defaultValue = "accuracy") String sort,
                                            @RequestParam(value = "lat", defaultValue = "35.137922") String lat,
                                            @RequestParam(value = "lng", defaultValue = "129.055628") String lng,
                                            Model model) throws IOException, URISyntaxException {

        return kakaoKwSearchService.getList(kw, sort, Double.parseDouble(lat),  Double.parseDouble(lng)) ;
    }

    @GetMapping("/add")
    @ResponseBody
    private KakaoResponseDto addWish(@RequestParam("item") String item,
                                           Principal principal) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        KakaoResponseDto kakaoResponseDto = mapper.readValue(item, KakaoResponseDto.class);
        wishList.add(0, kakaoResponseDto);

        return  kakaoResponseDto;
    }


}
