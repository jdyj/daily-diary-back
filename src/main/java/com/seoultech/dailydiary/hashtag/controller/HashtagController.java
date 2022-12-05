package com.seoultech.dailydiary.hashtag.controller;

import com.seoultech.dailydiary.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HashtagController {

  private final HashtagService hashtagService;

}
