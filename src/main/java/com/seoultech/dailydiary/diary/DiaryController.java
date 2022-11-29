package com.seoultech.dailydiary.diary;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

  private final DiaryService diaryService;

  @PostMapping
  public void save() {

  }

  @GetMapping("/my")
  public void listMyDiary() {

  }

  @GetMapping("/public")
  public void listPublicDiary() {

  }

  @GetMapping("/like")
  public void listLikeDiary() {

  }

}
