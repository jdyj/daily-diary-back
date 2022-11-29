package com.seoultech.dailydiary.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoultech.dailydiary.TokenDto;
import com.seoultech.dailydiary.jwt.TokenProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements
    AuthenticationSuccessHandler {

  private final TokenProvider tokenProvider;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    TokenDto tokenDto = tokenProvider.generateToken(authentication);
    response.setHeader("accessToken", tokenDto.getAccessToken());
    response.setHeader("refreshToken", tokenDto.getRefreshToken());
  }
}
