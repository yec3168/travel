package com.busan.travel.dto;

import com.busan.travel.status.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserFormDto {
    private Long id;

    private String name;

    private String nickName;

    private String email;

    private String password;

    private String address;

    private Gender gender;

    private LocalDateTime createDate; //회원생성날짜.



}
