package com.seoultech.dailydiary.diary;


import static com.seoultech.dailydiary.config.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static com.seoultech.dailydiary.config.jwt.JwtFilter.BEARER_PREFIX;

import com.seoultech.dailydiary.config.jwt.JwtConfig;
import com.seoultech.dailydiary.config.jwt.TokenProvider;
import com.seoultech.dailydiary.config.login.Auth;
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
import java.io.IOException;
import java.security.Key;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
@CrossOrigin
public class DiaryController {

  private final DiaryService diaryService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<Void> save(@ModelAttribute CreateDiaryRequest request,
      @ApiIgnore @Auth String memberId)
      throws IOException {
    Member member = memberService.findMemberById(memberId);
    diaryService.save(request.toEntity(member), request);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailDiary> detailDiary(@ApiIgnore @Auth String memberId,
      @PathVariable Long id) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(diaryService.detailDiary(member, id));
  }

  @GetMapping
  public ResponseEntity<DiaryListResponse> listPublicDiary(HttpServletRequest servletRequest,
      @RequestParam("sort") String sort,
      @RequestParam("limit") String limit, @RequestParam("lte") String lte) {

    String token = resolveToken(servletRequest);
    String memberId = validateToken(token);

    if (memberId == null) {
      return ResponseEntity.ok()
          .body(DiaryListResponse.from(
              diaryService.publicList(sort, Long.valueOf(limit), Long.valueOf(lte))));
    }
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(DiaryListResponse.from(
            diaryService.diaryList(member, sort, Long.valueOf(limit), Long.valueOf(lte))));
  }

  @GetMapping("/bookmark")
  public ResponseEntity<DiaryListResponse> listBookmarkDiary(@ApiIgnore @Auth String memberId,
      @RequestParam("sort") String sort,
      @RequestParam("limit") String limit, @RequestParam("lte") String lte) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(DiaryListResponse.from(
            diaryService.bookmarkDiaryList(member, sort, Long.valueOf(limit), Long.valueOf(lte))));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDiary(@PathVariable Long id, @ApiIgnore @Auth String memberId) {

    Member member = memberService.findMemberById(memberId);
    diaryService.deleteDiary(member, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
