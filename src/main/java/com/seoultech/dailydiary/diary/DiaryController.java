package com.seoultech.dailydiary.diary;


import com.seoultech.dailydiary.config.login.Auth;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.service.MemberService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

  private final DiaryService diaryService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<Void> save(@ModelAttribute CreateDiaryRequest request,
      @ApiIgnore @Auth String memberId)
      throws IOException {
    Member member = memberService.findMemberById(memberId);
    diaryService.save(request.toEntity(member), request.getTags(), request.getMultipartFile());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailDiary> detailDiary(@ApiIgnore @Auth String memberId,
      @PathVariable Long id) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(diaryService.detailDiary(member, id));
  }

  @GetMapping
  public ResponseEntity<DiaryListResponse> listPublicDiary(@ApiIgnore @Auth String memberId,
      @RequestParam String sort,
      @RequestParam Long limit, @RequestParam Long lte) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(DiaryListResponse.from(diaryService.diaryList(member, sort, limit, lte)));
  }

  @GetMapping("/bookmark")
  public ResponseEntity<DiaryListResponse> listBookmarkDiary(@ApiIgnore @Auth String memberId,
      @RequestParam String sort,
      @RequestParam Long limit, @RequestParam Long lte) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(DiaryListResponse.from(diaryService.bookmarkDiaryList(member, sort, limit, lte)));
  }

}
