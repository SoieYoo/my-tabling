package com.zerobase.mytabling.store.domain;

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

}

