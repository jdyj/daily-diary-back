package com.seoultech.dailydiary.member.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProfileImageRequest {

  private MultipartFile image;

}
