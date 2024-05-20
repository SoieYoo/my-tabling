package com.zerobase.mytabling.customer.controller;

import com.zerobase.mytabling.customer.domain.Review;
import com.zerobase.mytabling.customer.dto.ReviewDto;
import com.zerobase.mytabling.customer.service.ReviewService;
import com.zerobase.mytabling.store.domain.Store;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewController {

  private final ReviewService reviewService;

  /**
   * 리뷰 등록
   */
  @PostMapping
  public ResponseEntity<String> createReview(@Valid @RequestBody ReviewDto.Request reviewDto) {
    reviewService.createReview(reviewDto);
    return ResponseEntity.status(HttpStatus.CREATED).body("Review created successfully");
  }

  /**
   * 리뷰 조회
   */
  @GetMapping("/{reviewId}")
  public ReviewDto.Response getReviewsByStoreId(@PathVariable Long reviewId) {
    return reviewService.getReviewById(reviewId);
  }

  /**
   * 리뷰 수정
   */
  @PutMapping("/{reviewId}")
  public ResponseEntity<Review> updateReview(@RequestBody Review updatedReview,
      @PathVariable Long reviewId) {
    Review updated = reviewService.updateReview(reviewId, updatedReview);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
  }

  /**
   * 리뷰 삭제
   */
  @DeleteMapping("/{reviewId}")
  public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
    reviewService.deleteReview(reviewId);
    return ResponseEntity.status(HttpStatus.OK).body("Review deleted successfully");
  }
}
