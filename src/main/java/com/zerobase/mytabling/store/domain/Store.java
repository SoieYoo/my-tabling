package com.zerobase.mytabling.store.domain;

import com.zerobase.mytabling.store.dto.StoreDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String storeName;
  private String storeTelNumber;

  private LocalTime openTime;       // 개장 시간
  private LocalTime closeTime;      // 폐장 시간
  private int reservationTimeUnit;  // 예약 시간 단위

  private String street;
  private String city;
  private String postalCode;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "manager_id")
//  private Manager manager;


  private @CreatedDate LocalDateTime createdAt;
  private @LastModifiedDate LocalDateTime updatedAt;

  // Store 인스턴스를 생성하는 static 메서드
  public static Store createStore(StoreDto.Request storeRequest) {
    return Store.builder()
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
  }
}

