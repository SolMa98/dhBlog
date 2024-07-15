package com.dhkim.blog.config;

import com.dhkim.blog.login.jwt.JwtAuthenticationFilter;
import com.dhkim.blog.login.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                // 로그인 API
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/ajax/sign-in")
                                ).permitAll()
                                // Token check
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/ajax/token/check")
                                ).permitAll()
                                // 회원 가입 관련 Ajax 호출 API
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/ajax/account/**")
                                ).permitAll()
                                // 포스트 페이지
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/post/**")
                                ).permitAll()
                                // 로그인 페이지
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/login/**")
                                ).permitAll()
                                // 회원가입 페이지
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/account/**")
                                ).permitAll()
                                // 템플릿 정보 가져 올 수 있도록 허용
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/templates/**")
                                ).permitAll()
                                // page 정보 가져 올 수 있도록 허용
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/page/**")
                                ).permitAll()
                                // 블로그 업로드 이미지 가져 올 수 있도록 허용
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/upload/**")
                                ).permitAll()
                                // favicon
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/favicon.ico")
                                ).permitAll()
                                // post 관련 권한 설정
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/ajax/post/**")
                                ).hasRole("USER")
                                .anyRequest().authenticated()
                ).addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // https://suddiyo.tistory.com/entry/Spring-Spring-Security-JWT-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-2 참고
}
