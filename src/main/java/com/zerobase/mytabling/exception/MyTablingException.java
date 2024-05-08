package com.zerobase.mytabling.exception;

import com.zerobase.mytabling.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyTablingException extends RuntimeException {

  private final ErrorCode errorCode;
  private final String errorMessage;

  public MyTablingException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}
