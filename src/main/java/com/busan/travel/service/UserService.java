package com.busan.travel.service;

import com.busan.travel.dto.UserDto;
import com.busan.travel.entity.User;
import com.busan.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void createUser(UserDto userDto){
        Optional<User> op= userRepository.findByEmail(userDto.getEmail());
        if(op.isPresent()) {

            User user = User.builder()
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .name(userDto.getName())
                    .nickName(userDto.getNickName())
                    .address(userDto.getAddress())
                    .gender(userDto.getGender())
                    .build();

            userRepository.save(user);
        }
        else{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}
