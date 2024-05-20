package com.zerobase.mytabling.customer.dto;

import lombok.Getter;

public class LoginDto {
  @Getter
  public static class Request {
    private String email;
    private String password;
  }

}
