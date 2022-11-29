package com.seoultech.dailydiary.member;

import com.seoultech.dailydiary.image.Image;
import java.util.Random;
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

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  public String getRoleKey() {
    return this.role.getKey();
  }

  @Builder
  public Member(String name, String email, Role role) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.email = email;
    this.role = role;
  }

  public void setProfileImage(Image profileImage) {
    this.profileImage = profileImage;
  }

  protected Member() {
  }
}
