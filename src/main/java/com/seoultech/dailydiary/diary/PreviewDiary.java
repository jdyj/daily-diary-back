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
public class PreviewDiary {

  private Long diaryId;
  private String title;
  private String contents;
  private String createdAt;
  private List<String> tags;
  private String name;
  private String profileImage;
  private String image;
  private Author author;
  private Boolean isPublic;

  public static PreviewDiary from(Diary diary) {

    List<String> collectTags = diary.getDiaryHashtagList()
        .stream()
        .map((diaryHashtag) -> diaryHashtag.getHashtag().getTag())
        .collect(Collectors.toList());

    return new PreviewDiary(diary.getId(), diary.getTitle(), diary.getContents(),
        diary.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), collectTags,
        diary.getMember().getName(),
        diary.getMember().getProfileImage().getStoreFileName(),
        diary.getThumbnailImage().getStoreFileName(),
        Author.from(diary.getMember()),
        diary.getIsPublic());
  }

}
