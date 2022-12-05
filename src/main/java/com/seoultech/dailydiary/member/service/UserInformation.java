package com.seoultech.dailydiary.member.service;

import com.seoultech.dailydiary.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformation {

  private String memberId;

  private String name;

  private String email;

  private String profileImage;

  public static UserInformation from(Member member) {
    return new UserInformation(member.getId(), member.getName(), member.getEmail(),
        member.getProfileImage().getStoreFileName());
  }

}
