package com.ll.exam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // 모든 경로
                        .requestMatchers(new AntPathRequestMatcher("/**"))
                        // 허용 한다
                        .permitAll())
                        .csrf((csrf) -> csrf
                        // 이 패턴으로 들어온 url은 체크하지 않겠다
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                        .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                        .formLogin((formLogin) -> formLogin
                                .loginPage("/user/login") //시큐리티 로그인 폼을 이 url로 사용
                                .defaultSuccessUrl("/")); //로그인 성공하고 나면 보낼 페이지

        return http.build();
    }

    //스프링 시스템에 객체 등록
    //@Bean은 @Configuration라는 어노테이션을 가진 클래스에서만 사용 가능
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
