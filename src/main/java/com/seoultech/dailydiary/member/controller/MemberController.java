package com.seoultech.dailydiary.member.controller;

import com.seoultech.dailydiary.config.jwt.TokenDto;
import com.seoultech.dailydiary.config.login.Auth;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.service.MemberService;
import com.seoultech.dailydiary.member.service.UserInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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


}
