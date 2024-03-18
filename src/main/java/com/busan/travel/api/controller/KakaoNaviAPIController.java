package com.busan.travel.api.controller;

import com.busan.travel.api.dto.KakaoResponseDto;
import com.busan.travel.api.dto.NaviApiResponseDto;
import com.busan.travel.api.service.KakaoKwSearchService;
import com.busan.travel.api.service.NaviApiService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/kakao")
public class KakaoNaviAPIController {

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @Autowired
    private NaviApiService naviApiService;

    @Autowired
    private KakaoKwSearchService kakaoKwSearchService;

    @GetMapping("/road")
    public String roadHome(Model model){
        model.addAttribute("kakao_admin_key", kakao_admin_key);
        return "region/road";
    }

//    @GetMapping("/road/position")
//    @ResponseBody
//    public List<KakaoResponseDto> getPosition(@RequestParam("kw") String kw) throws UnsupportedEncodingException, URISyntaxException, ParseException {
//        return  kakaoKwSearchService.getList()
//    }
}
