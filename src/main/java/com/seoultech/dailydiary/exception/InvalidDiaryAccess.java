package com.seoultech.dailydiary.exception;

public class InvalidDiaryAccess extends RuntimeException {

  private static final String MESSAGE = "올바르지 않은 게시글 접근입니다.";

  public InvalidDiaryAccess() {
    super(MESSAGE);
  }
}

