package com.zerobase.mytabling.customer.service;

import com.zerobase.mytabling.cache.service.RedisService;
import com.zerobase.mytabling.customer.domain.Customer;
import com.zerobase.mytabling.customer.domain.Review;
import com.zerobase.mytabling.customer.dto.ReviewDto;
import com.zerobase.mytabling.customer.repository.CustomerRepository;
import com.zerobase.mytabling.customer.repository.ReviewRepository;
import com.zerobase.mytabling.store.domain.Reservation;
import com.zerobase.mytabling.store.domain.Store;
import com.zerobase.mytabling.store.repository.ReservationRepository;
import com.zerobase.mytabling.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReservationRepository reservationRepository;
  private final CustomerRepository customerRepository;
  private final StoreRepository storeRepository;
  private final RedisService redisService;

  /**
   * 리뷰 등록
   */
  @Transactional
  public void createReview(ReviewDto.Request request) {
    // 매장
    Store store = storeRepository.findById(request.getStoreId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid store ID"));

    // 고객
    Customer customer = customerRepository.findById(request.getCustomerId())
        .orElseThrow(() -> new EntityNotFoundException(
            "Customer not found with id: " + request.getCustomerId()));

    // 예약
    Reservation reservation = reservationRepository.findById(request.getReservationId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid reservation ID"));

    // 리뷰 생성
    Review review = Review.createReview(request, store, customer,
        reservation);

    // 리뷰 저장
    reviewRepository.save(review);

    // 리뷰 작성할때마다 redis 의 평균 별점 업데이트
//    double averageRating = updateRatingInRedis(store.getId(), review.getRating());
//    log.info("Average rating for store {} updated to: {}", store.getId(),
//        averageRating);

  }

  /**
   * redis 의 평균 별점 업데이트
   */
  private double updateRatingInRedis(Long storeId, double newRating) {
    // Redis 에서 기존 별점 데이터 검색
    Double existingRating = redisService.getRating(storeId);

    // 기존 평점이 없는 경우 새 평점을 매장 평점으로 설정
    if (existingRating == null) {
      redisService.saveRating(storeId, newRating);
      // 총 리뷰 수를 1로 설정
      redisService.saveTotalReviewsCount(storeId, 1);
      return newRating;
    } else {
      // 새로운 평균 평점 계산
      double totalRating = existingRating + newRating;
      long totalReviews = redisService.getTotalReviewsCount(storeId) + 1;
      log.info("total count: {}", totalReviews);
      double averageRating = totalRating / totalReviews;

      // 새로운 평균 별점으로 Redis 업데이트
      redisService.saveRating(storeId, averageRating);
      redisService.saveTotalReviewsCount(storeId, totalReviews);
      return averageRating;
    }
  }

  @Transactional(readOnly = true)
  public List<Long> getAllStoreIds() {
    return reviewRepository.findAllStoreIds(); // 모든 매장의 ID 가져오기
  }

  @Transactional(readOnly = true)
  public double calculateAverageRating(Long storeId) {
    return reviewRepository.calculateAverageRatingByStoreId(storeId); // 매장의 평균 별점 계산
  }

  /*
  public Double getAverageRating(Long storeId) {
    Double averageRating = redisService.getAverageRating(storeId);
    // redis 에 평균 별점 없을 경우
    if (averageRating == null) {
      // db 에서 조회 및 연산
      averageRating = reviewRepository.getAverageRating(storeId);
      // redis 에 평균 별점 저장
      redisService.saveRating(storeId, averageRating);
    }
    return averageRating;
  }
   */

}
