package com.seoultech.dailydiary.jwt;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = parseToken(request);

    if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
      Authentication authentication = tokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      log.debug("유효한 Jwt 토큰이 존재하지 않습니다.");
    }

    filterChain.doFilter(request, response);
  }

  private String parseToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
