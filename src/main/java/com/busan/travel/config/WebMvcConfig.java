package com.busan.travel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath.path}")
    String uploadPath; //실제 저장위치.

    @Value("${getPath.path}")
    String getPath; //url 위치

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // web브라우저에서 /images로 시작하는 경우 uploadPath로 설정한 경로의 파일을 읽어오도록 설정
        // 로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 지정.
        registry.addResourceHandler(getPath)
                .addResourceLocations(uploadPath);
    }
}
