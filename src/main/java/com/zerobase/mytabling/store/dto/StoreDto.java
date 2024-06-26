package com.zerobase.mytabling.store.dto;

import com.zerobase.mytabling.store.domain.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotBlank
    @Size(min = 1, max = 20)
    private String storeName;
    @NotBlank
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
    private String storeTelNumber;
    @NotNull
    private LocalTime openTime;
    @NotNull
    private LocalTime closeTime;
    @NotNull
    private int reservationTimeUnit;
    //    @NotBlank
    private Long managerId;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String postalCode;
  }

  @Getter
  @Builder
  public static class Response {
    private String storeName;
    private String storeTelNumber;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int reservationTimeUnit;
    private Long managerId;
    private String street;
    private String city;
    private String postalCode;
  }

  public static StoreDto.Response convertToDto(Store store) {
    return Response.builder()
        .storeName(store.getStoreName())
        .storeTelNumber(store.getStoreTelNumber())
        .openTime(store.getOpenTime())
        .closeTime(store.getCloseTime())
        .reservationTimeUnit(store.getReservationTimeUnit())
        .street(store.getStreet())
        .city(store.getCity())
        .postalCode(store.getPostalCode())
        .build();
  }
}
