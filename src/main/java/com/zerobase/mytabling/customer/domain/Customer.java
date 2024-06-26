package com.zerobase.mytabling.customer.domain;

import com.zerobase.mytabling.customer.dto.CustomerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String email;
  private String name;
  private String phoneNumber;
  private String password;

  private @CreatedDate LocalDateTime createdAt;

  public static Customer createCustomer(CustomerDto.Request request, String hashedPassword) {
    return Customer.builder()
        .email(request.getEmail())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .password(hashedPassword)
        .createdAt(LocalDateTime.now())
        .build();
  }

}