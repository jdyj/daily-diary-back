package com.seoultech.dailydiary.jwt;

import com.seoultech.dailydiary.TokenDto;
import com.seoultech.dailydiary.config.auth.CustomMemberDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24;
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30;

  private final Key key;

  public TokenProvider() {
    byte[] decode = Decoders.BASE64.decode(JwtConfig.JWT_SECRET);
    this.key = Keys.hmacShaKeyFor(decode);
  }

  public TokenDto generateToken(Authentication authentication) {

    CustomMemberDetails member = (CustomMemberDetails) authentication.getPrincipal();

    long now = (new Date()).getTime();

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

    String memberId = member.getName();
    String role = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    String accessToken = Jwts.builder()
        .setId(memberId)
        .setExpiration(accessTokenExpiresIn)
        .claim("role", role)
        .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS512)
        .compact();

    Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
    String refreshToken = Jwts.builder()
        .setId(member.getName())
        .setExpiration(refreshTokenExpiresIn)
        .claim("role", role)
        .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS512)
        .compact();

    return TokenDto.from(accessToken, refreshToken);
  }

  public TokenDto refreshAccessToken(String id) {
    long now = (new Date()).getTime();

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        .setId(id)
        .setExpiration(accessTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    return TokenDto.from(accessToken, "");
  }

  public Authentication getAuthentication(String accessToken) {

    Claims claims = parseClaims(accessToken);

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get("role").toString().split(","))
            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    CustomMemberDetails principal = new CustomMemberDetails(claims.getId(), "",
        authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);

  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }

    return false;
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(accessToken)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
