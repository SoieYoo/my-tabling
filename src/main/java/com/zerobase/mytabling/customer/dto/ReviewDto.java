package com.zerobase.mytabling.customer.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ReviewDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotNull
    private Long storeId;

    @NotNull
    private Long customerId;

    @NotNull
    private Long reservationId;

    @Min(0)
    @Max(5)
    private double rating;

    @NotBlank
    private String comment;
  }
}
