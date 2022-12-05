package com.seoultech.dailydiary.bookmark.controller;

import static com.seoultech.dailydiary.config.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static com.seoultech.dailydiary.config.jwt.JwtFilter.BEARER_PREFIX;

import com.seoultech.dailydiary.config.jwt.JwtConfig;
import com.seoultech.dailydiary.config.login.Auth;
import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.diary.DiaryService;
import com.seoultech.dailydiary.bookmark.controller.dto.LikeDiaryRequest;
import com.seoultech.dailydiary.bookmark.service.BookmarkService;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@CrossOrigin
public class BookmarkController {

  private final BookmarkService bookmarkService;
  private final MemberService memberService;
  private final DiaryService diaryService;

  @PostMapping
  public ResponseEntity<Void> likeDiary(HttpServletRequest servletRequest,
      @RequestBody LikeDiaryRequest request) {

    String token = resolveToken(servletRequest);
    String memberId = validateToken(token);

    Member member = memberService.findMemberById(memberId);
    Diary diary = diaryService.findById(request.getDiaryId());

    bookmarkService.bookmarkDiary(member, diary);
    return new ResponseEntity<>(HttpStatus.CREATED);
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
