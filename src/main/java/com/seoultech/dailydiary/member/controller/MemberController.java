package com.seoultech.dailydiary.member.controller;

import com.seoultech.dailydiary.config.login.Auth;
import com.seoultech.dailydiary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/mypage")
  public void myPage(@Auth String memberId) {

  }


}
