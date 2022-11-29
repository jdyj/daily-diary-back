package com.seoultech.dailydiary.like.repository;

import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.like.Like;
import com.seoultech.dailydiary.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Like.Key> {

  List<Like> findAllByMember(Member member);

  boolean existsLikeByKey(Like.Key key);

  void deleteAllByMember(Member member);

  Optional<Like> findHeartByMemberAndDiary(Member member, Diary diary);

}
