package com.seoultech.dailydiary.exception;

public class NotExistDiaryException extends RuntimeException {

  private static final String MESSAGE = "존재하지 않는 일기입니다.";

  public NotExistDiaryException() {
    super(MESSAGE);
  }
}
