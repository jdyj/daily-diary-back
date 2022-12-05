package com.seoultech.dailydiary.bookmark.repository;

import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.bookmark.Bookmark;
import com.seoultech.dailydiary.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Bookmark.Key> {

  List<Bookmark> findAllByMember(Member member);

  boolean existsByKey(Bookmark.Key key);

  void deleteAllByMember(Member member);

  Optional<Bookmark> findHeartByMemberAndDiary(Member member, Diary diary);

}
