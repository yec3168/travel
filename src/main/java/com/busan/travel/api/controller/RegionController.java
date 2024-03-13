package com.busan.travel.api.controller;

import com.busan.travel.api.dto.KakaoResponseDto;
import com.busan.travel.api.service.KakaoKwSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class RegionController {

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @Autowired
    private final KakaoKwSearchService kakaoKwSearchService;

//    @GetMapping("/test")
//    public String test(Model model){
//        model.addAttribute("kakao_admin_key", kakao_admin_key);
//        return "search";
//    }

    @GetMapping("/search")
    private String kwSearch(@PathVariable(value = "kw", required = false)String kw,
                                            Model model) throws IOException, URISyntaxException {
        model.addAttribute("kakao_admin_key", kakao_admin_key);
        model.addAttribute("keywordList", kakaoKwSearchService.getList(kw));
        return "region/search";
    }
}
