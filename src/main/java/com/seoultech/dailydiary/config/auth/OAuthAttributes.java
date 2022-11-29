package com.seoultech.dailydiary.config.auth;

import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.Role;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class OAuthAttributes {

  private final Map<String, Object> attributes;
  private final String name;
  private final String email;
  private final String nameAttributeKey;

  public Member toEntity() {
    return new Member(name, email, Role.USER);
  }

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
      String name, String email) {
    this.attributes = attributes;
    this.name = name;
    this.email = email;
    this.nameAttributeKey = nameAttributeKey;
  }

  public static OAuthAttributes of(String registrationId, String nameAttributeKey,
      Map<String, Object> attributes) {
    if ("kakao".equals(registrationId)) {
      return ofKakao(nameAttributeKey, attributes);
    }
    return ofGoogle(nameAttributeKey, attributes);
  }

  private static OAuthAttributes ofKakao(String nameAttributeKey,
      Map<String, Object> attributes) {

    Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
    return OAuthAttributes.builder()
        .name((String) profile.get("nickname"))
        .email((String) kakao_account.get("email"))
        .attributes(attributes)
        .nameAttributeKey(nameAttributeKey)
        .build();
  }

  public static OAuthAttributes ofGoogle(String nameAttributeKey,
      Map<String, Object> attributes) {
    // 미리 정의한 속성으로 빌드.
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .attributes(attributes)
        .nameAttributeKey(nameAttributeKey)
        .build();
  }

}
