package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Author {

  private String id;
  private String name;
  private String image;

  public static Author from(Member member) {
    return new Author(member.getId(), member.getName(),
        member.getProfileImage().getStoreFileName());
  }

}
