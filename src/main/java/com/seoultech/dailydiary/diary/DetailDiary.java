package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.member.Member;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailDiary {

  private Long id;
  private String title;
  private String content;
  private String createdAt;
  private Boolean isPublic;
  private Boolean isBookmark;
  private List<String> tags;
  private String image;
  private Author author;

  public static DetailDiary from(Diary diary, Boolean isBookmark) {
    List<String> collect = diary.getDiaryHashtagList().stream()
        .map((diaryHashtag) -> diaryHashtag.getHashtag().getTag())
        .collect(Collectors.toList());

    return new DetailDiary(diary.getId(), diary.getTitle(), diary.getContents(),
        diary.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        diary.getIsPublic(), isBookmark, collect,
        diary.getThumbnailImage().getStoreFileName(), Author.from(diary.getMember()));
  }

}
