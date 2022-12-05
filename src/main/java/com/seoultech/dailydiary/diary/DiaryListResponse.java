package com.seoultech.dailydiary.diary;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DiaryListResponse {

  private List<?> data;
  private int count;

  public static DiaryListResponse from(List<?> collect) {
    return new DiaryListResponse(collect, collect.size());
  }

}
