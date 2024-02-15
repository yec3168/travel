package com.busan.travel.service;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.User;
import com.busan.travel.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void createUser(UserFormDto userFormDto){
        Optional<User> op= userRepository.findByEmail(userFormDto.getEmail());
        if(!op.isPresent()) {
            User user = User.createUser(userFormDto, passwordEncoder);
            System.out.println(user.getPassword());
            userRepository.save(user);
        }
        else{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 로그인시 이메일 매핑.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> op = userRepository.findByEmail(email);
        if(op.isPresent()){
            User findUser = op.get();
            if(findUser == null)
                throw new UsernameNotFoundException(email);

            return org.springframework.security.core.userdetails.User.builder()
                    .username(findUser.getEmail())
                    .password(findUser.getPassword())
                    .roles(findUser.getUserRole().toString())
                    .build();
        }

        return null;
    }

}
