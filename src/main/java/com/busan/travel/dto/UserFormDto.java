package com.busan.travel.dto;

import com.busan.travel.entity.User;
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

    private String filename;

    private String url;


    public static UserFormDto toDto(User user){
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setId(user.getId());
        userFormDto.setName(user.getName());
        userFormDto.setNickName(user.getNickName());
        userFormDto.setEmail(user.getEmail());
        userFormDto.setPassword(user.getPassword());
        userFormDto.setAddress(user.getAddress());
        userFormDto.setGender(user.getGender());
        userFormDto.setCreateDate(user.getCreateDate());
        userFormDto.setFilename(user.getFilename());
        userFormDto.setUrl(user.getUrl());

        return userFormDto;
    }


}
