package com.seoultech.dailydiary.diary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateDiaryRequest {

  private String title;

  private String contents;

  private String thumbnailImage;

}
