package com.busan.travel.dto;

import com.busan.travel.entity.Board;
import com.busan.travel.entity.Member;
import com.busan.travel.status.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberFormDto {
    private Long id;

    private String name;

    private String nickName;

    private String email;

    private String password;

    private String address;

    private Gender gender;

    private LocalDateTime createDate; //회원생성날짜.

    // 대표이미지
    private String filename;

    private String url;

    //작성한 자유게시판 게시글.
    private List<Board> boardList = new ArrayList<>();

    public static MemberFormDto toDto(Member user){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setId(user.getId());
        memberFormDto.setName(user.getName());
        memberFormDto.setNickName(user.getNickName());
        memberFormDto.setEmail(user.getEmail());
        memberFormDto.setPassword(user.getPassword());
        memberFormDto.setAddress(user.getAddress());
        memberFormDto.setGender(user.getGender());
        memberFormDto.setCreateDate(user.getCreateDate());
        memberFormDto.setFilename(user.getFilename());
        memberFormDto.setUrl(user.getUrl());
        memberFormDto.setBoardList(user.getBoardList());

        return memberFormDto;
    }


}
