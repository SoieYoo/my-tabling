package com.zerobase.mytabling.manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoginDto {
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Request {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

  }

  @AllArgsConstructor
  @Getter
  public static class Response {
    private String email;
    private String role;

    public Response(ManagerDto loggedInManager) {
      this.email = loggedInManager.getEmail();
      this.role = loggedInManager.getRole();
    }
  }
}
