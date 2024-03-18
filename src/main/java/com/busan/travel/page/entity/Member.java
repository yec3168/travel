package com.busan.travel.page.entity;

import com.busan.travel.api.dto.KakaoResponseDto;
import com.busan.travel.page.dto.MemberFormDto;
import com.busan.travel.page.status.Gender;
import com.busan.travel.page.status.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String nickName;

    private String email;

    private String password;

    private String address;

    private LocalDateTime createDate; //회원생성날짜.

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    // 대표이미지.
    private String filename;

    private String url;


    //사용자가 작성한 자유게시판 게시글.
    @OneToMany(mappedBy = "writer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Board> boardList;


    @Builder
    public Member(String email, String password ,String name,
                 String nickName, String address, Gender gender,
                 UserRole userRole, LocalDateTime createDate,
                String filename, String url){
        this.email =email;
        this.password = password;
        this.name = name;
        this.nickName =nickName;
        this.address =address;
        this.gender =gender;
        this.userRole = userRole;
        this.createDate= createDate;
        this.filename =filename;
        this.url = url;
    }



    public static Member createUser(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder,
                                    String filename, String url, UserRole role) {
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        Member member = Member.builder()
                .name(memberFormDto.getName())
                .nickName(memberFormDto.getNickName())
                .email(memberFormDto.getEmail())
                .password(password)
                .address(memberFormDto.getAddress())
                .gender(memberFormDto.getGender())
                .userRole(role)
                .filename(filename)
                .url(url)
                .createDate(LocalDateTime.now()).build();
        return member;
    }

    public void updateImg(String filename, String url){
            this.filename=filename;
            this.url=url;
    }
}
