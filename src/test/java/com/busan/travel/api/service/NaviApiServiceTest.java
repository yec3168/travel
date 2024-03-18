package com.busan.travel.api.service;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NaviApiServiceTest {


    @Autowired
    private NaviApiService naviApiService;

    @Test
    public void Test() throws UnsupportedEncodingException, URISyntaxException, ParseException {
        String qurey = "부산역";
        System.out.println(naviApiService.getPlace(qurey).toString());
    }
}