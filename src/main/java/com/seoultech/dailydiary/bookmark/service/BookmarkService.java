package com.seoultech.dailydiary.bookmark.service;

import com.seoultech.dailydiary.bookmark.Bookmark.Key;
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

  private final BookmarkRepository bookmarkRepository;

  public void bookmarkDiary(Member member, Diary diary) {
    if (bookmarkRepository.existsById(new Key(member.getId(), diary.getId()))) {
      bookmarkRepository.deleteById(new Key(member.getId(), diary.getId()));
    } else {
      bookmarkRepository.save(new Bookmark(member, diary));
    }
  }


}
