package com.seoultech.dailydiary.like.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dailydiary.like.repository.LikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

  private final LikeRepository heartRepository;


}
