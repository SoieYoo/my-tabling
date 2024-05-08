package com.zerobase.mytabling.exception;

import static com.zerobase.mytabling.type.ErrorCode.INTERVAL_SERVER_ERROR;
import static com.zerobase.mytabling.type.ErrorCode.INVALID_REQUEST;
import static com.zerobase.mytabling.type.ErrorCode.INVALID_INPUT;

import com.zerobase.mytabling.type.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MyTablingException.class)
  protected ErrorResponse handlerAccountException(MyTablingException e) {
    log.error("{} is occurred.", e.getErrorCode());
    return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("handleMethodArgumentNotValidException occurred: {}", e.getMessage());
    String errorMessage = e.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getDefaultMessage())
        .findFirst()
        .orElse("Validation failed");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
        INVALID_INPUT, errorMessage));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    log.error("MethodArgumentTypeMismatchException occurred: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
        INVALID_REQUEST, INVALID_REQUEST.getDescription()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handlerException(Exception e) {
    log.error("Exception is occurred.", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse(INTERVAL_SERVER_ERROR,
            INTERVAL_SERVER_ERROR.getDescription()));
  }
}
