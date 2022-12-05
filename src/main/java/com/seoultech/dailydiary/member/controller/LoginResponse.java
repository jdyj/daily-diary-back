package com.seoultech.dailydiary.member.controller;


import com.seoultech.dailydiary.config.jwt.TokenDto;
import com.seoultech.dailydiary.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

  private String accessToken;

  private String refreshToken;

  private String memberId;

  private String name;

  private String profileImage;

  private String email;

  public static LoginResponse from(TokenDto tokenDto, Member member) {
    return new LoginResponse(tokenDto.getAccessToken(), tokenDto.getRefreshToken(), member.getId(),
        member.getName(), member.getProfileImage().getStoreFileName(), member.getEmail());
  }

}
