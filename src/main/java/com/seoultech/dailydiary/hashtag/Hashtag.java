package com.seoultech.dailydiary.hashtag;

import com.seoultech.dailydiary.postHashtag.DiaryHashtag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(indexes = @Index(name = "i_tag", columnList = "tag"))
public class Hashtag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String tag;

  @OneToMany(mappedBy = "hashtag")
  private List<DiaryHashtag> diaryHashtagList = new ArrayList<>();

  public Hashtag(String tag) {
    this.tag = tag;
  }

  protected Hashtag() {
  }
}
