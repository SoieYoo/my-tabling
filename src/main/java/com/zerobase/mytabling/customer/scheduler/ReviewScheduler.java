package com.zerobase.mytabling.customer.scheduler;

import com.zerobase.mytabling.cache.service.RedisService;
import com.zerobase.mytabling.customer.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewScheduler {

  private final ReviewService reviewService;
  private final RedisService redisService;

//  @Scheduled(fixedDelay = 60 * 60 * 1000) // 한 시간마다 실행
//  @Scheduled(cron = "0 0/5 * * * *") // 5분마다 실행
//  @Scheduled(fixedDelay = 2 * 60 * 1000) // 2분마다 실행
  @Scheduled(cron = "0 0 * * * *") // 매 시간마다 실행
  @Transactional
  public void updateAverageRatings() {
    log.info("### review scheduler start ###");
    List<Long> storeIds = reviewService.getAllStoreIds(); // 모든 매장의 ID 가져오기

    for (Long storeId : storeIds) {
      double averageRating = reviewService.calculateAverageRating(storeId); // 매장의 평균 별점 계산
      redisService.saveRating(storeId, averageRating); // Redis에 평균 별점 저장
    }
  }

}
