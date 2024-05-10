package com.zerobase.mytabling.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  INVALID_INPUT("유효성 검증 실패입니다."),
  INVALID_REQUEST("잘못된 요청입니다."),
  INTERVAL_SERVER_ERROR("내부 서버 오류가 발생했습니다.");
  private final String description;
}
