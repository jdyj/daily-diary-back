package com.seoultech.dailydiary.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.io.IOException;
import java.security.Key;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

@Slf4j
public class JwtFilter implements Filter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  private static final String[] whitelist = {"/api/v1/images", "/api/v1/members/login",
      "/docs/api-doc.html", "/favicon.ico",
      "/swagger-resources/**", "/v3/api-docs", "/swagger*/**", "/webjars/**", "/swagger-resources",
      "/api/v1/diary",
      "/api/v1/diary/**",
      "/api/v1/images/**",
      "/api/v1/members/**"};

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    try {
      HttpServletRequest httpRequest = (HttpServletRequest) request;

      String jwt = resolveToken(httpRequest);
      String requestURI = httpRequest.getRequestURI();
      String method = httpRequest.getMethod();

      // 2. validateToken 으로 토큰 유효성 검사
      if (isCheckPath(requestURI, method) && (!validateToken(jwt, httpRequest)
          || !StringUtils.hasText(jwt))) {
        throw new IllegalAccessException("유효하지 않는 토큰입니다");
      }
      chain.doFilter(request, response);
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | IllegalAccessException e) {
      HttpServletResponse res = (HttpServletResponse) response;
      res.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());

    } catch (Exception e) {
      HttpServletResponse res = (HttpServletResponse) response;
      res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
  }

  private boolean isCheckPath(String requestURI, String method) {
    if (requestURI.equals("/api/v1/diary") && method.equals("POST")) {
      return true;
    }

    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }

  // Request Header 에서 토큰 정보를 꺼내오기
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }


  private boolean validateToken(String token, HttpServletRequest servletRequest) {

    byte[] decode = Decoders.BASE64.decode(JwtConfig.JWT_SECRET);
    Key key = Keys.hmacShaKeyFor(decode);

    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
      servletRequest.setAttribute("memberId", claims.get("jti", String.class));
      return true;
    } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {

      throw e;
    }
  }
}