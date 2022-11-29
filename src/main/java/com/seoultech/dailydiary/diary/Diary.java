package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.image.Image;
import com.seoultech.dailydiary.like.Like;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Diary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String contents;

  @OneToOne
  private Image thumbnailImage;

  @OneToMany(mappedBy = "diary")
  private List<Like> likeList;

}
