package com.seoultech.dailydiary.diary;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

  @Query("select d from Diary d where d.id > :id order by d.createdDate asc")
  List<Diary> findDiariesGreaterThanId(@Param("id") Long id);

  @Query("select d from Diary d where d.id < :id order by d.createdDate desc")
  List<Diary> findDiariesLessThanId(@Param("id") Long id);
}
