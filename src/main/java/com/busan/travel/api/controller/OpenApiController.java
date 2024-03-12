package com.busan.travel.api.controller;

import com.busan.travel.api.service.OpenApiService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.UnsupportedOrmXsdVersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenApiController {

    @Autowired
    private final OpenApiService openApiService;
    @GetMapping("/open-api")
    public Object fetch() throws UnsupportedEncodingException, URISyntaxException {
        return openApiService.getEntity().getBody();
    }

    @GetMapping("/open")
    public Object test() throws UnsupportedEncodingException, ParseException, URISyntaxException {
        return openApiService.getJson().toString();
    }
}
