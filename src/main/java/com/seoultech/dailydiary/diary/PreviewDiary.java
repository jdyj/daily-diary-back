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
  private String image;
  private Author author;
  private Boolean isPublic;
  private List<String> bookmarkUser;

  public static PreviewDiary from(Diary diary) {

    List<String> collectTags = diary.getDiaryHashtagList()
        .stream()
        .map((diaryHashtag) -> diaryHashtag.getHashtag().getTag())
        .collect(Collectors.toList());

    List<String> bookmarkUser = diary.getBookmarkList().stream()
        .map(bookmark -> bookmark.getMember().getId()).collect(Collectors.toList());

    return new PreviewDiary(diary.getId(),
        diary.getTitle(),
        diary.getContents(),
        diary.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        collectTags,
        diary.getMember().getName(),
        diary.getThumbnailImage() == null ? null : diary.getThumbnailImage().getStoreFileName(),
        Author.from(diary.getMember()),
        diary.getIsPublic(), bookmarkUser);
  }

}
