package com.seoultech.dailydiary.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seoultech.dailydiary.config.jwt.TokenDto;
import com.seoultech.dailydiary.config.jwt.TokenProvider;
import com.seoultech.dailydiary.exception.NotExistMemberException;
import com.seoultech.dailydiary.image.Image;
import com.seoultech.dailydiary.image.ImageService;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final TokenProvider tokenProvider;
  private final ImageService imageService;
  private final RestTemplate restTemplate;

  public MyPage findMyPage(String memberId) {
    return null;
  }

  public Member findMemberById(String memberId) {
    return memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
  }

  public TokenDto login(String accessToken) {
    String apiUrl = "https://kapi.kakao.com/v2/user/me";
    String responseBody = get(apiUrl, accessToken);

    JsonParser parser = new JsonParser();
    JsonElement element = parser.parse(responseBody);

    JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
    JsonObject profile = kakaoAccount.getAsJsonObject().get("profile").getAsJsonObject();

    String name = profile.getAsJsonObject().get("nickname").getAsString();
    String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

    Optional<Member> memberOptional = memberRepository.findByEmail(email);

    Member member;
    if (memberOptional.isPresent()) {
      member = memberOptional.get();
    } else {
      Image profileImage = imageService.findDefaultProfileImage();
      member = memberRepository.save(new Member(name, email));
      member.setProfileImage(profileImage);
    }

    return tokenProvider.generateToken(member.getId());
  }

  private String get(String apiUrl, String accessToken) {

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + accessToken);

      HttpEntity entity = new HttpEntity(headers);
      ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity,
          String.class);

      return response.getBody();
    } catch (HttpStatusCodeException e) {
      throw new RuntimeException("API 요청과 응답 실패", e);
    }
  }

}
