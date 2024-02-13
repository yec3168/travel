package com.busan.travel.service;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.User;
import com.busan.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void createUser(UserFormDto userFormDto){
        Optional<User> op= userRepository.findByEmail(userFormDto.getEmail());
        if(!op.isPresent()) {
            User user = User.createUser(userFormDto, passwordEncoder);
            userRepository.save(user);
        }
        else{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}
