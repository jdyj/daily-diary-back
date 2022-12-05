package com.seoultech.dailydiary.postHashtag.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.hashtag.Hashtag;
import com.seoultech.dailydiary.postHashtag.DiaryHashtag;
import com.seoultech.dailydiary.postHashtag.repository.DiaryHashtagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryHashtagService {

  private final DiaryHashtagRepository diaryHashtagRepository;

  public void create(Diary diary, List<Hashtag> hashtags) {
    diaryHashtagRepository.saveAll(hashtags.stream()
        .map(hashtag -> new DiaryHashtag(diary, hashtag))
        .collect(toList()));
  }

  public List<DiaryHashtag> saveAll(List<DiaryHashtag> postHashtags) {
    return diaryHashtagRepository.saveAll(postHashtags);
  }

  public DiaryHashtag save(DiaryHashtag postHashtag) {
    return diaryHashtagRepository.save(postHashtag);
  }

  public List<DiaryHashtag> findPostHashtags(Hashtag hashtag) {
    return diaryHashtagRepository.findPostHashtagsByHashtag(hashtag);
  }

  public void deletePostHashtag(Diary diary) {
    diaryHashtagRepository.deleteAllByDiary(diary);
  }


}
