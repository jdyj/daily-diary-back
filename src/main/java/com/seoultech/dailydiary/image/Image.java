package com.seoultech.dailydiary.image;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalFilename;

  private String storeFileName;

  @Enumerated(EnumType.STRING)
  private Category category;

  public Image(String originalFilename, String storeFileName,
      Category category) {
    this.originalFilename = originalFilename;
    this.storeFileName = storeFileName;
    this.category = category;
  }

  public String getResizeFileName(int width, int height) {
    int pos = this.storeFileName.lastIndexOf(".");
    String ext = extractExt();
    return this.storeFileName.substring(0, pos) + "_" + width + "x" + height + "." + ext;
  }

  private String extractExt() {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }

  protected Image() {
  }


}
