package com.zerobase.mytabling.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다."),
  USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
  USER_PASSWORD_UN_MATCH("비밀번호가 일치하지 않습니다."),
  INVALID_INPUT("유효성 검증 실패입니다."),
  INVALID_REQUEST("잘못된 요청입니다."),
  RESERVATION_NOT_FOUND("예약을 찾을 수 없습니다."),
  INTERVAL_SERVER_ERROR("내부 서버 오류가 발생했습니다.");
  private final String description;
}
