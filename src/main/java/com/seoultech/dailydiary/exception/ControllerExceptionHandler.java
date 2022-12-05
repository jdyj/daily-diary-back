package com.seoultech.dailydiary.exception;

import com.seoultech.dailydiary.exception.dto.BadRequestFailResponse;
import com.seoultech.dailydiary.exception.dto.ForbiddenFailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {
      NotExistMemberException.class,
      NotExistDiaryException.class
  })
  public ResponseEntity<BadRequestFailResponse> notFound(Exception e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(BadRequestFailResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build()
        );
  }

  @ExceptionHandler(value = {
      InvalidDiaryAccess.class
  })
  public ResponseEntity<ForbiddenFailResponse> forbidden(Exception e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ForbiddenFailResponse.builder()
            .status(HttpStatus.FORBIDDEN.value())
            .message(e.getMessage())
            .build()
        );
  }

}
