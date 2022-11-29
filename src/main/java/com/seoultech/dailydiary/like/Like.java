package com.seoultech.dailydiary.like;


import com.seoultech.dailydiary.BaseTimeEntity;
import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.member.Member;
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
public class Like extends BaseTimeEntity {


  @EmbeddedId
  private Key key;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("memberId")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("diaryId")
  private Diary diary;

  public Like(Member member, Diary diary) {
    this.member = member;
    this.diary = diary;
    key = new Key(member.getId(), diary.getId());
    diary.getLikeList().add(this);
  }

  @Embeddable
  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  public static class Key implements Serializable {

    private String memberId;
    private Long diaryId;

    protected Key() {
    }
  }

  protected Like() {
  }
}
