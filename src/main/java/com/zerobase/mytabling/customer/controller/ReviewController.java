package com.zerobase.mytabling.customer.controller;

import com.zerobase.mytabling.customer.dto.ReviewDto;
import com.zerobase.mytabling.customer.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping("/review")
  public ResponseEntity<String> createReview(@Valid @RequestBody ReviewDto.Request reviewDto) {
    reviewService.createReview(reviewDto);
    return ResponseEntity.status(HttpStatus.CREATED).body("Review created successfully");
  }
}
