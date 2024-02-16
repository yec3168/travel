package com.busan.travel.service;

import com.busan.travel.dto.UserFormDto;
import com.busan.travel.entity.User;
import com.busan.travel.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileService fileService;

    @Value("${imgSave.location}")
    private String uploadImage; //file:///C:/travel/

    public void createUser(UserFormDto userFormDto, MultipartFile multipartFile){
        Optional<User> op= userRepository.findByEmail(userFormDto.getEmail());
        if(!op.isPresent()) {
            String filename;
            String dbUrl;
            if(multipartFile.isEmpty()){
                dbUrl = "/images/default_profile.png";
                filename = "default_profile.png";
            }else{
                filename =fileService.uuidFileName(multipartFile);
                imageSave(multipartFile, filename);
                dbUrl = "/image/user/"+filename;
            }

            User user = User.createUser(userFormDto, passwordEncoder, filename, dbUrl);
            System.out.println(user.getPassword());
            userRepository.save(user);
        }
        else{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public void imageSave(MultipartFile multipartFile, String filename){
        String checkUser = uploadImage+"/user/"; // file:///C:/travel/user 저장위치
        fileService.makeFile(checkUser); //폴더없으면 생성

        String uploadUrl =checkUser+filename; // t
        try{
            fileService.saveFile(multipartFile, uploadUrl);
        }catch (Exception e) {
            throw new IllegalStateException("파일을 생성할 수 없습니다.");
        }

    }

    public Optional<User> getUser(String email){
        return userRepository.findByEmail(email);
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
