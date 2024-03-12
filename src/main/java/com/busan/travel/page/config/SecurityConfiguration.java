package com.busan.travel.page.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private AuthenticationFailureHandler CustomAuthFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin(form -> form
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/") //로그인 성공시
                .passwordParameter("password")
                .usernameParameter("email") // 로그인시 사용할 파라미터 email로 설정.
                .failureHandler(CustomAuthFailureHandler)
        );
        http.logout(form -> form
                .logoutSuccessUrl("/")
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")));

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }



}

//        http.authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/","/user/login","/user/signup",
//                        "/css/**", "/js/**","/images/**").permitAll()
//                .requestMatchers("/user/logout").authenticated()
//                .anyRequest().authenticated()
//        );
