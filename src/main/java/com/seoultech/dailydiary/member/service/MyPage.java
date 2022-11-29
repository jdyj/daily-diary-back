package com.seoultech.dailydiary.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPage {

  private String memberId;

  private Integer participationCount;

  private Integer friendCount;

  private Integer SuccessRate;

  private String dowithCode;

//  public static MyPage from(Member member) {
//    return new MyPage(member.getId(), );
//  }

}
