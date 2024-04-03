package com.busan.travel.api.controller;

import com.busan.travel.api.dto.KakaoResponseDto;
import com.busan.travel.api.dto.LinePathDto;
import com.busan.travel.api.entity.Wish;
import com.busan.travel.api.service.KakaoKwSearchService;
import com.busan.travel.api.service.NaviApiService;
import com.busan.travel.page.entity.Member;
import com.busan.travel.page.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/kakao")
public class RegionController {

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @Autowired
    private  KakaoKwSearchService kakaoKwSearchService;

    @Autowired
    private NaviApiService naviApiService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/search")
    public String searchHome(Model model){
        model.addAttribute("kakao_admin_key", kakao_admin_key);
        return "tourist/search";
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


    @PostMapping("/add")
    @ResponseBody
    private KakaoResponseDto addWish(@RequestParam("item") String item,
                                           Principal principal) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        KakaoResponseDto kakaoResponseDto = mapper.readValue(item, KakaoResponseDto.class);

        //해당 사용자 위시리스트 추가.
        if(principal != null){
            Member member = memberService.getUserByEmail(principal.getName());
            kakaoKwSearchService.addWishList(kakaoResponseDto, member);
        }

        return  kakaoResponseDto;
    }

    @GetMapping("/wishes")
    @ResponseBody
    private List<KakaoResponseDto> findWishList(Principal principal){
       if(principal != null){
           Member member = memberService.getUserByEmail(principal.getName());
           List<KakaoResponseDto> wishList=  kakaoKwSearchService.findAllByMember(member);
           return wishList;
       }
       return null;
    }

    //길찾기 부분.
    @GetMapping("/road")
    public String roadHome(Model model){
        model.addAttribute("kakao_admin_key", kakao_admin_key);
        return "tourist/road";
    }

    @GetMapping("/car")
    @ResponseBody
    public List<LinePathDto> getLinePath(@RequestParam("origin")String origin,
                                         @RequestParam("originX")String originX,
                                         @RequestParam("originY")String originY,
                                         @RequestParam("destination")String destination,
                                         @RequestParam("destinationX")String destinationX,
                                         @RequestParam("destinationY")String destinationY) throws UnsupportedEncodingException, URISyntaxException, ParseException {
        System.out.println(origin + originX + originY+ destination + destinationX+ destinationY);
        return naviApiService.getPlace(origin, originX, originY, destination, destinationX, destinationY);
    }


}
