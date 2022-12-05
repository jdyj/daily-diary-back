package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.member.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CreateDiaryRequest {

  private String title;

  private String contents;

  private List<String> tags;

  private Boolean isPublic;

  private MultipartFile multipartFile;

  public Diary toEntity(Member member) {
    return new Diary(title, contents, member, isPublic);
  }

}
