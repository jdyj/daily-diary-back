package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.member.Member;
import java.time.LocalDateTime;
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
  private LocalDateTime createdDate;
  private List<String> tags;
  private String name;
  private String profileImage;
  private String thumbnailImage;

  public static PreviewDiary from(Diary diary, Member member) {

    List<String> collectTags = diary.getDiaryHashtagList()
        .stream()
        .map((diaryHashtag) -> diaryHashtag.getHashtag().getTag())
        .collect(Collectors.toList());

    return new PreviewDiary(diary.getId(), diary.getTitle(), diary.getContents(),
        diary.getCreatedDate(), collectTags, member.getName(),
        member.getProfileImage().getStoreFileName(),
        diary.getThumbnailImage().getStoreFileName());
  }

}
