package com.seoultech.dailydiary.bookmark.service;

import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.bookmark.Bookmark;
import com.seoultech.dailydiary.bookmark.repository.BookmarkRepository;
import com.seoultech.dailydiary.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

  private final BookmarkRepository likeRepository;

  public void likeDiary(Member member, Diary diary) {
    likeRepository.save(new Bookmark(member, diary));
  }


}
