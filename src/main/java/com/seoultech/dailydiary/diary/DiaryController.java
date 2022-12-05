package com.seoultech.dailydiary.diary;


import com.seoultech.dailydiary.config.login.Auth;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.service.MemberService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    diaryService.save(request.toEntity(member), request);
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
  public ResponseEntity<DiaryListResponse> listPublicDiary(HttpServletRequest servletRequest,
      @RequestParam("sort") String sort,
      @RequestParam("limit") String limit, @RequestParam("lte") String lte) {

    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(DiaryListResponse.from(
            diaryService.diaryList(member, sort, Long.valueOf(limit), Long.valueOf(lte))));
  }

  @GetMapping("/bookmark")
  public ResponseEntity<DiaryListResponse> listBookmarkDiary(@ApiIgnore @Auth String memberId,
      @RequestParam("sort") String sort,
      @RequestParam("limit") String limit, @RequestParam("lte") String lte) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(DiaryListResponse.from(
            diaryService.bookmarkDiaryList(member, sort, Long.valueOf(limit), Long.valueOf(lte))));
  }

}
