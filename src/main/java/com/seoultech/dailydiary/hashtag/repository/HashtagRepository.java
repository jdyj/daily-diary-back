package com.seoultech.dailydiary.hashtag.repository;

import com.seoultech.dailydiary.hashtag.Hashtag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  @Query("select h from Hashtag h where h.tag in :tags")
  List<Hashtag> findByTags(@Param("tags") List<String> tags);

  Optional<Hashtag> findByTag(String tag);

}
