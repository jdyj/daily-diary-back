package com.seoultech.dailydiary.like.controller;

import com.seoultech.dailydiary.diary.DiaryService;
import com.seoultech.dailydiary.like.service.LikeService;
import com.seoultech.dailydiary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

  private final LikeService likeService;
  private final MemberService memberService;
  private final DiaryService diaryService;

  @PostMapping
  public void likeDiary() {

  }


}
