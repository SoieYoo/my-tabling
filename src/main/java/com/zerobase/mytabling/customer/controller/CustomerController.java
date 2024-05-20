package com.zerobase.mytabling.customer.controller;

import com.zerobase.mytabling.customer.dto.CustomerDto;
import com.zerobase.mytabling.customer.dto.LoginDto;
import com.zerobase.mytabling.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  /**
   * 회원 가입
   */
  @PostMapping("/register")
  public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDto.Request request) {
    customerService.createCustomer(request);
    return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
  }

  /**
   * 로그인
   */
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Valid LoginDto.Request request) {
    // 로그인 서비스 호출
    customerService.login(request);
    return ResponseEntity.status(HttpStatus.CREATED).body("Customer login successfully");
  }
}
