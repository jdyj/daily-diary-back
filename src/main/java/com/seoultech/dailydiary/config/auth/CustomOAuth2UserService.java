package com.seoultech.dailydiary.config.auth;

import com.seoultech.dailydiary.image.ImageService;
import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final MemberRepository memberRepository;
  private final ImageService imageService;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    //DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    //OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값 네이버 카카오 지원 x
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();

    //OAuth2UserService를 통해 가져온 데이터를 담을 클래스
    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
        oAuth2User.getAttributes());

    //로그인 한 유저 정보
    Member member = saveOrUpdate(attributes);

    // 로그인한 유저를 리턴함
    return CustomMemberDetails.create(member, oAuth2User.getAttributes());
  }

  //User 저장하고 이미 있는 데이터면 Update
  private Member saveOrUpdate(OAuthAttributes attributes) {
    Member member = memberRepository.findByEmail(attributes.getEmail())
        .orElse(attributes.toEntity());
    member.setProfileImage(imageService.findDefaultProfileImage());
    return memberRepository.save(member);
  }
}