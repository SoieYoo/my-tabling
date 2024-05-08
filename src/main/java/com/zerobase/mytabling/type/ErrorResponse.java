package com.zerobase.mytabling.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private ErrorCode errorCode;
  private String errorMessage;
}
