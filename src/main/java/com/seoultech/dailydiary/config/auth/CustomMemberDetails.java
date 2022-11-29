package com.seoultech.dailydiary.config.auth;

import com.seoultech.dailydiary.member.Member;
import com.seoultech.dailydiary.member.Role;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomMemberDetails implements UserDetails, OAuth2User {

  private String id;
  private String email;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public CustomMemberDetails(String id, String email,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.authorities = authorities;
  }

  public static CustomMemberDetails create(Member member) {
    List<GrantedAuthority> authorities = Collections.
        singletonList(new SimpleGrantedAuthority(Role.USER.getKey()));

    return new CustomMemberDetails(
        member.getId(),
        member.getEmail(),
        authorities
    );
  }

  public static CustomMemberDetails create(Member member, Map<String, Object> attributes) {
    CustomMemberDetails userDetails = CustomMemberDetails.create(member);
    userDetails.setAttributes(attributes);
    return userDetails;
  }


  @Override
  public String getName() {
    return this.id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
}
