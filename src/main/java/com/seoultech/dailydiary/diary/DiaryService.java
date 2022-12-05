package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.bookmark.Bookmark;
import com.seoultech.dailydiary.exception.InvalidDiaryAccess;
import com.seoultech.dailydiary.exception.NotExistDiaryException;
import com.seoultech.dailydiary.hashtag.Hashtag;
import com.seoultech.dailydiary.hashtag.service.BookmarkDiary;
import com.seoultech.dailydiary.hashtag.service.HashtagService;
import com.seoultech.dailydiary.image.Category;
import com.seoultech.dailydiary.image.Image;
import com.seoultech.dailydiary.image.ImageService;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.postHashtag.service.DiaryHashtagService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

  private final DiaryRepository diaryRepository;
  private final DiaryHashtagService diaryHashtagService;
  private final HashtagService hashtagService;
  private final ImageService imageService;

  public void save(Diary diary, CreateDiaryRequest request) throws IOException {
    Diary savedDiary = diaryRepository.save(diary);
    if (request.getTags() != null) {
      List<Hashtag> hashtag = hashtagService.createHashtag(request.getTags());
      diaryHashtagService.create(savedDiary, hashtag);
    }
    if (request.getImage() != null) {
      savedDiary.setThumbnailImage(imageService.storeFile(request.getImage(), Category.DIARY));
    }
  }

  public Diary findById(Long diaryId) {
    return diaryRepository.findById(diaryId)
        .orElseThrow(NotExistDiaryException::new);
  }

  public List<PreviewDiary> bookmarkDiaryList(Member member, String sort, Long limit, Long lte) {
    List<Diary> diaryList = member.getBookmarkList()
        .stream()
        .map(Bookmark::getDiary)
        .collect(Collectors.toList());
    if (lte != null) {
      diaryList = diaryList.stream().filter(diary -> diary.getId() > lte)
          .collect(Collectors.toList());
    }

    if (limit != null) {
      diaryList = diaryList.stream()
          .limit(limit)
          .collect(Collectors.toList());
    }

    if (sort.equals("ASC")) {
      diaryList.sort(Comparator.comparing(Diary::getCreatedDate));
    } else {
      diaryList.sort((a1, a2) -> a2.getCreatedDate().compareTo(a1.getCreatedDate()));
    }

    List<PreviewDiary> collect = new ArrayList<>();
    for (Diary diary : diaryList) {
      if (!diary.getIsPublic()) {
        if (diary.getMember().getId().equals(member.getId())) {
          collect.add(PreviewDiary.from(diary, diary.getMember()));
        }
      } else {
        collect.add(PreviewDiary.from(diary, diary.getMember()));
      }
    }

    return collect;
  }

  public List<PreviewDiary> publicList(String sort, Long limit, Long lte) {
    List<Diary> diaryList;
    if (lte == null) {
      diaryList = diaryRepository.findAll();
    } else {
      diaryList = diaryRepository.findDiariesGreaterThanId(lte);
    }

    if (limit != null) {
      diaryList = diaryList.stream()
          .limit(limit)
          .collect(Collectors.toList());
    }
    if (sort.equals("ASC")) {
      diaryList.sort(Comparator.comparing(Diary::getCreatedDate));
    } else {
      diaryList.sort((a1, a2) -> a2.getCreatedDate().compareTo(a1.getCreatedDate()));
    }

    List<PreviewDiary> collect = new ArrayList<>();
    for (Diary diary : diaryList) {
      if (diary.getIsPublic()) {
        collect.add(PreviewDiary.from(diary, diary.getMember()));
      }
    }

    return collect;
  }

  public List<PreviewDiary> diaryList(Member member, String sort, Long limit, Long lte) {
    List<Diary> diaryList;
    if (lte == null) {
      diaryList = diaryRepository.findAll();
    } else {
      diaryList = diaryRepository.findDiariesGreaterThanId(lte);
    }

    if (limit != null) {
      diaryList = diaryList.stream()
          .limit(limit)
          .collect(Collectors.toList());
    }
    if (sort.equals("ASC")) {
      diaryList.sort(Comparator.comparing(Diary::getCreatedDate));
    } else {
      diaryList.sort((a1, a2) -> a2.getCreatedDate().compareTo(a1.getCreatedDate()));
    }

    List<PreviewDiary> collect = new ArrayList<>();
    for (Diary diary : diaryList) {
      if (!diary.getIsPublic()) {
        if (diary.getMember().getId().equals(member.getId())) {
          collect.add(PreviewDiary.from(diary, diary.getMember()));
        }
      } else {
        collect.add(PreviewDiary.from(diary, diary.getMember()));
      }
    }

    return collect;
  }

  public DetailDiary detailDiary(Member member, Long diaryId) {
    Diary diary = findById(diaryId);
    if (!diary.getIsPublic() && !diary.getMember().getId().equals(member.getId())) {
      throw new InvalidDiaryAccess();
    }
    boolean isBookmark = false;
    for (Bookmark bookmark : diary.getBookmarkList()) {
      if (bookmark.getMember().getId().equals(member.getId())) {
        isBookmark = true;
        break;
      }
    }

    return DetailDiary.from(diary, member, isBookmark);
  }

  public void deleteDiary(Member member, Long diaryId) {
    Diary diary = findById(diaryId);
    if (diary.getMember().getId().equals(member.getId())) {
      diaryRepository.delete(diary);
    } else {
      throw new IllegalStateException();
    }

  }

}
