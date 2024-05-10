package com.zerobase.mytabling.store.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zerobase.mytabling.store.domain.Store;
import com.zerobase.mytabling.store.dto.StoreDto;
import com.zerobase.mytabling.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

  @InjectMocks
  private StoreService storeService;

  @Mock
  private StoreRepository storeRepository;

  @BeforeEach
  void setUp() {
    storeRepository.deleteAll();
    reset(storeRepository);
  }

  @Test
  @DisplayName("매장 등록 성공")
  void registerStore_Success() {
    // Given
    StoreDto.Request storeRequest = StoreDto.Request.builder()
        .storeName("매장1")
        .storeTelNumber("010-1234-5678")
        .openTime(LocalTime.of(9, 0, 0))
        .closeTime(LocalTime.of(21, 0, 0))
        .reservationTimeUnit(30)
        .street("서울특별시 강남구 테헤란로")
        .city("서울")
        .postalCode("12345")
        .build();

    Store.builder()
        .storeName(storeRequest.getStoreName())
        .storeTelNumber(storeRequest.getStoreTelNumber())
        .openTime(storeRequest.getOpenTime())
        .closeTime(storeRequest.getCloseTime())
        .reservationTimeUnit(storeRequest.getReservationTimeUnit())
        .street(storeRequest.getStreet())
        .city(storeRequest.getCity())
        .postalCode(storeRequest.getPostalCode())
        .createdAt(LocalDateTime.now())
        .build();

    when(storeRepository.save(any())).thenAnswer(invocation -> {
      Store storeToSave = invocation.getArgument(0);
      storeToSave.setId(1L); // 저장된 매장의 ID를 설정
      return storeToSave;
    });

    // When
    Long storeId = storeService.registerStore(storeRequest);

    // Then
    assertNotNull(storeId);
    assertEquals(1L, storeId); // 가짜 ID 값과 비교
    verify(storeRepository, times(1)).save(any());
  }
}