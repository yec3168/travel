package com.busan.travel.service;

import com.busan.travel.DataNotFoundException;
import com.busan.travel.dto.MemberFormDto;
import com.busan.travel.entity.Member;
import com.busan.travel.repository.MemberRepository;

import com.busan.travel.status.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileService fileService;

    @Value("${imgSave.location}")
    private String uploadImage; //file:///C:/travel/

    public void createUser(MemberFormDto memberFormDto, MultipartFile multipartFile){
        Optional<Member> op= memberRepository.findByEmail(memberFormDto.getEmail());
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
            UserRole role;
            if(memberFormDto.getEmail().toLowerCase().equals("admin"))
                role = UserRole.ADMIN;
            else
                role = UserRole.USER;
                Member user = Member.createUser(memberFormDto, passwordEncoder, filename, dbUrl, role);

            System.out.println(user.getPassword());
            memberRepository.save(user);
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

    public Member getUserByEmail(String email){
        Optional<Member> op = memberRepository.findByEmail(email);
        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("User Not Found");
    }

    public Optional<Member> getUser(String email){
        return memberRepository.findByEmail(email);
    }

    // 로그인시 이메일 매핑.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> op = memberRepository.findByEmail(email);
        Member findUser = op.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(email.toLowerCase())){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.toString()));
        }
        return new User(findUser.getEmail(), findUser.getPassword(), authorities);

    }

}
