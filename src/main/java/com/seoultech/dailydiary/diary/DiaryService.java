package com.seoultech.dailydiary.diary;

import com.seoultech.dailydiary.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

  private final DiaryRepository diaryRepository;
  private final ImageService imageService;

  public void save() {
  }

}
