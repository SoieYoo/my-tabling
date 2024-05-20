package com.zerobase.mytabling.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CustomerDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotBlank
    @Email
    private String email;

    @Size(min = 1, max = 20)
    private String name;

    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
    private String phoneNumber;

    @NotBlank
    private String password;

  }

}
