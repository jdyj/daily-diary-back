package com.seoultech.dailydiary.postHashtag.repository;


import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.hashtag.Hashtag;
import com.seoultech.dailydiary.postHashtag.DiaryHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryHashtagRepository extends JpaRepository<DiaryHashtag, DiaryHashtag.Key> {

  List<DiaryHashtag> findPostHashtagsByHashtag(Hashtag hashtag);

  void deleteAllByDiary(Diary diary);

}
