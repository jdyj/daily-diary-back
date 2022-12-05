package com.seoultech.dailydiary.config;

import com.seoultech.dailydiary.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

  private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/**").permitAll()
        .anyRequest().permitAll();

    http
        .formLogin().disable()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(oAuth2UserService)
        .and()
        .successHandler(authenticationSuccessHandler);

//    http
//        .exceptionHandling()
//        .accessDeniedHandler(accessDeniedHandler())
//        .authenticationEntryPoint(authenticationEntryPoint());

    return http.build();
  }

}
