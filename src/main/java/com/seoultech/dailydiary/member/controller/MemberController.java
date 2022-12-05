package com.seoultech.dailydiary.member.controller;

import static com.seoultech.dailydiary.config.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static com.seoultech.dailydiary.config.jwt.JwtFilter.BEARER_PREFIX;

import com.seoultech.dailydiary.config.jwt.JwtConfig;
import com.seoultech.dailydiary.config.login.Auth;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.service.MemberService;
import com.seoultech.dailydiary.member.service.UserInformation;
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
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@CrossOrigin
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok()
        .body(memberService.login(request.getAccessToken()));
  }

  @PostMapping("/profile")
  public ResponseEntity<Void> setProfileImage(HttpServletRequest servletRequest,
      @ModelAttribute ProfileImageRequest request) throws IOException {

    String token = resolveToken(servletRequest);
    String memberId = validateToken(token);

    Member member = memberService.findMemberById(memberId);
    memberService.setProfileImage(member, request.getImage());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<UserInformation> myInfo(@ApiIgnore @Auth String memberId) {
    return ResponseEntity.ok()
        .body(UserInformation.from(memberService.findMemberById(memberId)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserInformation> userInfo(@PathVariable String id) {
    return ResponseEntity.ok()
        .body(UserInformation.from(memberService.findMemberById(id)));
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private String validateToken(String token) {

    byte[] decode = Decoders.BASE64.decode(JwtConfig.JWT_SECRET);
    Key key = Keys.hmacShaKeyFor(decode);

    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
      return claims.get("jti", String.class);
    } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      throw e;
    }
  }


}
