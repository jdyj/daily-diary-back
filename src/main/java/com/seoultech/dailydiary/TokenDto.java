package com.seoultech.dailydiary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

  private String accessToken;

  private String refreshToken;

  public static TokenDto from(String accessToken, String refreshToken) {
    return new TokenDto(accessToken, refreshToken);
  }

  public static TokenDto from(TokenDto tokenDto) {
    return new TokenDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
  }

}

