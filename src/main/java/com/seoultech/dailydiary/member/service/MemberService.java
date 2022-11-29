package com.seoultech.dailydiary.member.service;

import com.seoultech.dailydiary.exception.NotExistMemberException;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;

  public MyPage findMyPage(String memberId) {

    return null;
  }

  public Member findMemberById(String memberId) {
    return memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
  }

}
