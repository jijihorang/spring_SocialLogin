package org.example.sociallogin.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@Log4j2
@EnableMethodSecurity(prePostEnabled = true) // 메서드 수준의 보안을 활성화하는 설정
@RequiredArgsConstructor
public class CustomSecurityConfig {

    // dataSource : 데이터베이스 연결 정보를 가지고 있는 객체
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("filterChain -----------------------------");

        // 폼 기반 로그인 방식을 활성화
        http.formLogin(config -> { });

        // CSRF 보호 기능을 비활성화
        http.csrf(config -> {
            config.disable();
        });

        // 로그인 세션을 유지시키는 "Remember Me" 기능을 설정
        http.rememberMe(config -> {
            config.tokenValiditySeconds(60 * 60 * 24 * 30); // 토큰의 유효 기간을 설정
        });

        // 로그아웃 기능
        http.logout(config -> { });

        // OAuth2 로그인 기능을 설정
        http.oauth2Login(config -> { });

        return http.build();
    }

    // PasswordEncoder : 사용자 비밀번호를 암호화하고 검증하는 데 사용
    @Bean
    public PasswordEncoder passwordEncoder() {

        // BCryptPasswordEncoder : 비밀번호를 암호화할 때 널리 사용되는 강력한 해시 함수를 제공
        return new BCryptPasswordEncoder();
    }

    // 로그인 상태를 유지하기 위한 토큰 정보를 데이터베이스에 저장하고 관리
    // PersistentTokenRepository : 로그인 상태를 유지하기 위한 토큰을 저장하고 관리하는 인터페이스
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        // JDBC 기반으로 토큰을 데이터베이스에 저장하고 불러옴
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();

        // Remember Me 토큰을 저장할 데이터베이스와 연결
        tokenRepository.setDataSource(dataSource);

        // 데이터베이스에 Remember Me 토큰을 저장하고, 토큰을 기반으로 사용자 인증을 처리
        return tokenRepository;
    }
}
