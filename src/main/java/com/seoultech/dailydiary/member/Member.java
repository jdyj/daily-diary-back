package com.seoultech.dailydiary.member;

import com.seoultech.dailydiary.bookmark.Bookmark;
import com.seoultech.dailydiary.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member {

  @Id
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @OneToOne(fetch = FetchType.EAGER)
  private Image profileImage;

  @OneToMany(mappedBy = "member")
  private List<Bookmark> bookmarkList = new ArrayList<>();

  @Builder
  public Member(String name, String email) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.email = email;
  }

  public void setProfileImage(Image profileImage) {
    this.profileImage = profileImage;
  }

  protected Member() {
  }
}
