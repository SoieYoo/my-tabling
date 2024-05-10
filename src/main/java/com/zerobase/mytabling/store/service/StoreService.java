package com.zerobase.mytabling.store.service;

import com.zerobase.mytabling.cache.service.RedisService;
import com.zerobase.mytabling.store.domain.Store;
import com.zerobase.mytabling.store.dto.StoreDto;
import com.zerobase.mytabling.store.repository.StoreRepository;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

  private final StoreRepository storeRepository;
  private final RedisService redisService;
//  private final ManagerRepository managerRepository;

  /**
   * 매장 등록
   */
  public Long registerStore(StoreDto.Request storeRequest) {
    try {
      // 매장 저장
      Store savedStore = storeRepository.save(Store.createStore(storeRequest));

      log.info("Store saved successfully : {}", savedStore.getId());
      return savedStore.getId();

    } catch (Exception e) {
      log.error("Failed to register store", e);
      throw e;
    }
  }

  /**
   * 별점 높은순, 낮은 순 매장 검색
   */
  public List<Store> getStoresSortedByRating(boolean ascending) {

    // 평균 별점 오름차순,내림차순에 따라 매장 id 가져오기
    Set<String> storeIds = redisService.getSortedStoreIds(ascending);
    log.info("sorted store ids : {}", storeIds);

    // 매장 ID Long으로 변환
    List<Long> storeIdsAsLong = storeIds.stream()
        .map(Long::valueOf)
        .collect(Collectors.toList());

    log.info("store id convert string to Long : {}", storeIdsAsLong);

    List<Store> stores = storeRepository.findAllByIdIn(storeIdsAsLong);
    List<Long> storeIdsFromStores = stores.stream()
        .map(Store::getId)
        .collect(Collectors.toList());
    log.info("repository find store IDs: {}", storeIdsFromStores);

    return storeRepository.findAllByIdIn(storeIdsAsLong);
  }

}
