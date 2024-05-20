package com.zerobase.mytabling.customer.repository;

import com.zerobase.mytabling.customer.domain.Review;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT DISTINCT r.store.id FROM Review r")
  List<Long> findAllStoreIds(); // 모든 매장의 ID 가져오기

  @Query("SELECT AVG(r.rating) FROM Review r WHERE r.store.id = :storeId")
  Double calculateAverageRatingByStoreId(Long storeId); // 매장의 평균 별점 계산


}
