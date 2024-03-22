package com.busan.travel.api.service;

import com.busan.travel.OpenData.service.GoodRestaurantService;
import com.busan.travel.OpenData.service.RecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@SpringBootTest
@Transactional
class RecommendServiceTest {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private GoodRestaurantService goodRestaurantService;

    @Test
    public void test_api() throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        System.out.println(recommendService.getPlace().toString());
    }

    @Test
    public void t() throws UnsupportedEncodingException, URISyntaxException {
        System.out.println(goodRestaurantService.getJson().toString());
    }

}