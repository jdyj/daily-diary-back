package com.seoultech.dailydiary.postHashtag;

import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.hashtag.Hashtag;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
public class DiaryHashtag {

  @EmbeddedId
  private Key key;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("hashtagId")
  private Hashtag hashtag;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("diaryId")
  private Diary diary;

  public DiaryHashtag(Diary diary, Hashtag hashtag) {
    this.diary = diary;
    this.hashtag = hashtag;
    diary.getDiaryHashtagList().add(this);
    hashtag.getDiaryHashtagList().add(this);
    key = new Key(hashtag.getId(), diary.getId());
  }

  @Embeddable
  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  public static class Key implements Serializable {

    private Long hashtagId;
    private Long diaryId;

    protected Key() {
    }
  }

  protected DiaryHashtag() {
  }

}
