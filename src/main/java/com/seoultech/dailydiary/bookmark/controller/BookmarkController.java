package com.seoultech.dailydiary.bookmark.controller;

import com.seoultech.dailydiary.config.login.Auth;
import com.seoultech.dailydiary.diary.Diary;
import com.seoultech.dailydiary.diary.DiaryService;
import com.seoultech.dailydiary.bookmark.controller.dto.LikeDiaryRequest;
import com.seoultech.dailydiary.bookmark.service.BookmarkService;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

  private final BookmarkService bookmarkService;
  private final MemberService memberService;
  private final DiaryService diaryService;

  @PostMapping
  public ResponseEntity<Void> likeDiary(@ApiIgnore @Auth String memberId,
      @RequestBody LikeDiaryRequest request) {
    Member member = memberService.findMemberById(memberId);
    Diary diary = diaryService.findById(request.getDiaryId());

    bookmarkService.bookmarkDiary(member, diary);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


}
