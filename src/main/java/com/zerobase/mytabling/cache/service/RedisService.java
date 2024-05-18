package com.zerobase.mytabling.cache.service;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  /**
   * 매장 별점 저장
   */
  public void saveRating(Long storeId, double rating) {
    redisTemplate.opsForZSet().add("store_ratings", String.valueOf(storeId), rating);
  }

  /**
   * 매장 별점 조회
   */
  public Double getRating(Long storeId) {
    return redisTemplate.opsForZSet().score("store_ratings", String.valueOf(storeId));
  }

  /**
   * 매장 ID 에 대한 모든 리뷰 개수 저장
   */
  public void saveTotalReviewsCount(Long storeId, long totalReviews) {
    redisTemplate.opsForHash()
        .put("store_total_reviews", String.valueOf(storeId), String.valueOf(totalReviews));
  }

  /**
   * 매장 ID 에 대한 모든 리뷰 개수 조회
   */
  public long getTotalReviewsCount(Long storeId) {
//    Long totalReviews = (Long) redisTemplate.opsForHash().get("store_total_reviews", storeId);
//    return totalReviews != null ? totalReviews : 0;
    String totalReviewsStr = (String) redisTemplate.opsForHash()
        .get("store_total_reviews", storeId.toString());
    return totalReviewsStr != null ? Long.parseLong(totalReviewsStr) : 0;
  }

  /**
   * 평균별점 내림차순 조회
   */
  public Set<String> getSortedStoreIdsDesc() {
    return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange("store_ratings", 0, -1))
        .stream()
        .map(Object::toString)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * 매장 평균 별점 조회
   */
  public Set<String> getSortedStoreIds(boolean ascending) {
    if (ascending) {  // 오름차순
      return Objects.requireNonNull(redisTemplate.opsForZSet().range("store_ratings", 0, -1))
          .stream()
          .map(Object::toString)
          .collect(Collectors.toCollection(LinkedHashSet::new));
    } else {          // 내림차순
      return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange("store_ratings", 0, -1))
          .stream()
          .map(Object::toString)
          .collect(Collectors.toCollection(LinkedHashSet::new));
    }
  }

  /*
  public Double getAverageRating(Long storeId) {
    return redisTemplate.opsForZSet().score("store_ratings", storeId);
  }

  public Set<Object> getTopRatedStores(int count) {
    return redisTemplate.opsForZSet().reverseRange("store_ratings", 0, count - 1);
  }

  private String getRatingKey(Long storeId) {
    return "store_rating:" + storeId;
  }

  private String getTotalReviewsKey(Long storeId) {
    return "store_total_reviews:" + storeId;
  }
   */

}
