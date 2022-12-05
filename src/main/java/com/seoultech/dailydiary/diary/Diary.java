package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.BaseTimeEntity;
import com.seoultech.dailydiary.image.Image;
import com.seoultech.dailydiary.bookmark.Bookmark;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.postHashtag.DiaryHashtag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Diary extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String contents;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @OneToMany(mappedBy = "diary")
  private List<DiaryHashtag> diaryHashtagList = new ArrayList<>();

  @OneToOne
  private Image thumbnailImage;

  @OneToMany(mappedBy = "diary")
  private List<Bookmark> bookmarkList = new ArrayList<>();

  private Boolean isPublic;

  public void setThumbnailImage(Image thumbnailImage) {
    this.thumbnailImage = thumbnailImage;
  }

  public Diary(String title, String contents, Member member, Boolean isPublic) {
    this.title = title;
    this.contents = contents;
    this.member = member;
    this.isPublic = isPublic;
  }

  protected Diary() {
  }
}
