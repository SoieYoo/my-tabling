package com.zerobase.mytabling.store.service;

import com.zerobase.mytabling.store.domain.Store;
import com.zerobase.mytabling.store.dto.StoreDto;
import com.zerobase.mytabling.store.repository.StoreRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

  private final StoreRepository storeRepository;
//  private final ManagerRepository managerRepository;

  /**
   * 매장 등록
   */
  public void registerStore(StoreDto.Request storeRequest) {
    try {

      // 매장 저장
      storeRepository.save(Store.builder()
          .storeName(storeRequest.getStoreName())
          .storeTelNumber(storeRequest.getStoreTelNumber())
          .openTime(storeRequest.getOpenTime())
          .closeTime(storeRequest.getCloseTime())
          .reservationTimeUnit(storeRequest.getReservationTimeUnit())
//          .manager(managerRepository.findById(storeRequest.getManagerId())
//              .orElseThrow(() -> new IllegalArgumentException(
//                  "Manager with ID " + storeRequest.getManagerId() + " not found")))
          .createdAt(LocalDateTime.now())
          .build());

      log.info("Store saved successfully");

    } catch (Exception e) {
      log.error("Failed to register store", e);
      throw e;
    }
  }

}
