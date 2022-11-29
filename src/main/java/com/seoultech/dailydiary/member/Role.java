package com.seoultech.dailydiary.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

  GUSET("ROLE_GUEST", "손님"),
  USER("ROLE_USER", "사용자");

  private final String key;
  private final String title;

}
