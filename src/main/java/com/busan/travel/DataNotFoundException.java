package com.busan.travel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException{
    private static final long serialNumber =1L;

    public DataNotFoundException(String errorMsg){
        super(errorMsg);
    }
}