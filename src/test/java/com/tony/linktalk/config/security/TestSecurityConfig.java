package com.tony.linktalk.config.security;

import com.tony.linktalk.config.security.http.filter.JwtTokenFilter;
import com.tony.linktalk.config.security.http.user.UserDetailsImpl;
import com.tony.linktalk.config.security.http.user.UserDetailsServiceImpl;
import com.tony.linktalk.util.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile("test") // "test" 프로파일에만 적용
public class TestSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public TestSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // 사용자 세부 정보를 로드할 UserDetailsService 설정
        authProvider.setPasswordEncoder(passwordEncoder());     // 비밀번호를 인코딩 및 검증할 PasswordEncoder 설정
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers("/ws/**").permitAll() // WebSocket 엔드포인트 허용
                .and()
                .authenticationProvider(authenticationProvider()) // 실제 인증 제공자 사용
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 토큰 필터 추가

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenProvider(), userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 실제 사용될 JwtTokenProvider를 설정 (이 부분은 실제 구현에 맞게 조정)
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        // JwtTokenProvider 초기화 코드
        return new JwtTokenProvider();
    }

}
