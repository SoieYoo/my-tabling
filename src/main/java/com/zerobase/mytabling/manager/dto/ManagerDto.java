package com.zerobase.mytabling.manager.dto;

import com.zerobase.mytabling.manager.domain.Manager;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
  @Email
  private String email;
  @Size(min = 2, max = 20)
  private String name;
  @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
  private String phoneNumber;
  private String password;

  private String role;

  private LocalDateTime createdAt;

  public ManagerDto(String email, String name, String phoneNumber, String password) {
    this.email = email;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  public static ManagerDto fromEntity(Manager manager) {
    return ManagerDto.builder()
        .email(manager.getEmail())
        .name(manager.getName())
        .phoneNumber(manager.getPhoneNumber())
        .password(manager.getPassword())
        .createdAt(manager.getCreatedAt())
        .build();
  }
}
