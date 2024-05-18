package com.zerobase.mytabling.store.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservationDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotNull
    private Long storeId;

    @NotNull
    private Long customerId;

    @Future
    private LocalDateTime reservationDateTime;

    @Min(1)
    private int bookingPeopleCount;
  }

}
