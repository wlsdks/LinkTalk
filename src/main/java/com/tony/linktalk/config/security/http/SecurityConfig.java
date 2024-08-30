package com.tony.linktalk.config.security.http;

import com.tony.linktalk.config.security.http.filter.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스: 웹 보안 구성을 정의
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
@Profile("!test") // "test" 프로파일이 아닌 경우에만 활성화 (테스트 코드에서는 전용 설정이 있음)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;


    /**
     * @return DaoAuthenticationProvider
     * @apiNote DAO 인증 제공자 설정. (기본 로그인 인증)
     * DaoAuthenticationProvider는 AuthenticationManager의 구성 요소 중 하나로, 실제 사용자 인증을 수행하는 역할을 합니다.
     * DaoAuthenticationProvider는 retrieveUser 메서드를 사용하여 UserDetailsService.loadUserByUsername을 호출합니다.
     * retrieveUser 메서드에서 첫번째 매게변수인 username은 UsernamePasswordAuthenticationToken을 생성할때 담아준 첫번째 매개변수(이메일)가 전달됩니다.
     * new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()) -> (principal, credentials)
     * <br>
     * <p>
     * 1. retrieveUser 메서드를 통해 사용자를 로드합니다. <br>
     * 2. additionalAuthenticationChecks 메서드를 통해 비밀번호를 검증합니다. <br>
     * 3. 비밀번호가 일치하면 createSuccessAuthentication 메서드를 호출하여 인증된 UsernamePasswordAuthenticationToken 객체를 생성합니다 <br>
     * 4. 생성된 UsernamePasswordAuthenticationToken 객체는 principal로 UserDetailsImpl 객체를 설정합니다. <br>
     * 5. 로그인 할때 generateToken 메서드로 JWT 토큰을 생성할때 사용자 정보를 가져오기 위해 authentication.getPrincipal() 메서드를 호출합니다. <br>
     * </p>
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // 사용자 세부 정보를 로드할 UserDetailsService 설정
        authProvider.setPasswordEncoder(passwordEncoder());     // 비밀번호를 인코딩 및 검증할 PasswordEncoder 설정
        return authProvider;
    }


    /**
     * @param authenticationConfiguration 인증 구성 객체
     * @return AuthenticationManager
     * @throws Exception 예외
     * @apiNote 인증 관리자 설정. (Provider 지정 및 인증 처리) <br>
     * AuthenticationManager는 여러 AuthenticationProvider를 사용하여 인증을 처리할 수 있는 중앙 관리자입니다.
     * 지금은 provider로 DaoAuthenticationProvider를 사용하고 있습니다. <br>
     * 그래서 AuthenticationManager.authenticate 메서드가 호출되면 내부적으로 DaoAuthenticationProvider.authenticate를 호출합니다.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Spring Security가 설정한 AuthenticationManager 반환
    }


    /**
     * @return PasswordEncoder
     * @apiNote 비밀번호 인코더 설정. (BCryptPasswordEncoder)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain
     * @throws Exception 예외
     * @apiNote 보안 필터 체인 설정. (spring security 설정)
     * 필터 체인에서 JWT를 parsing하는 도중 예외가 발생하면 JwtEntryPoint가 호출되어 예외처리가 된다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)                                 // CSRF 보호 비활성화
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint)) // 인증 예외 처리 (custom 예외 처리: AuthenticationEntryPointImpl 클래스)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 상태 비저장 설정 (JWT 토큰 사용)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/member/**").permitAll()               // 인증 없이 접근 가능한 경로 설정
                            .anyRequest().authenticated();                         // 나머지 요청은 인증 필요
                })
                .authenticationProvider(authenticationProvider())                  // DaoAuthenticationProvider를 인증 제공자로 설정 (커스텀 로그인 인증)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT 토큰 필터 추가 (UsernamePasswordAuthenticationFilter 앞에 위치)

        return http.build();
    }

}
