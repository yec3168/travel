package com.busan.travel.dto;

import com.busan.travel.status.Gender;
import com.busan.travel.status.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickName;

    private String address;

    private LocalDateTime createDate; //회원생성날짜.

    private Gender gender;

}
