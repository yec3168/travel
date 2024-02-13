package com.busan.travel.entity;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.status.Gender;
import com.busan.travel.status.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickName;

    private String address;

    private LocalDateTime createDate; //회원생성날짜.

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Builder
    private User(String email, String password ,String name,
                 String nickName, String address, Gender gender,
                 UserRole userRole, LocalDateTime createDate){
        this.email =email;
        this.password = password;
        this.name = name;
        this.nickName =nickName;
        this.address =address;
        this.gender =gender;
        this.userRole = userRole;
        this.createDate= createDate;
    }

    public static User createUser(UserFormDto userFormDto, PasswordEncoder passwordEncoder){
        User user = User.builder()
                .name(userFormDto.getName())
                .nickName(userFormDto.getNickName())
                .email(userFormDto.getEmail())
                .password(passwordEncoder.encode(userFormDto.getPassword()))
                .address(userFormDto.getAddress())
                .gender(userFormDto.getGender())
                .userRole(UserRole.USER)
                .createDate(LocalDateTime.now()).build();

        return user;
    }


}
