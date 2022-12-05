package com.seoultech.dailydiary.member.controller;

import com.seoultech.dailydiary.config.jwt.TokenDto;
import com.seoultech.dailydiary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok()
        .body(memberService.login(request.getAccessToken()));
  }


}
