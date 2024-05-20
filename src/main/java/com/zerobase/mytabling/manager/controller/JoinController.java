package com.zerobase.mytabling.manager.controller;

import com.zerobase.mytabling.manager.dto.LoginDto;
import com.zerobase.mytabling.manager.dto.ManagerDto;
import com.zerobase.mytabling.manager.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class JoinController {

  private final JoinService joinService;

  /**
   * 관리자 회원 가입
   */
  @PostMapping("/register")
  public ManagerDto createCustomer(@RequestBody @Valid ManagerDto request) {

    return joinService.createManager(request);
  }

  /**
   * 관리자 로그인
   */
  @PostMapping("/login")
  public LoginDto.Response login(@RequestBody @Valid LoginDto.Request loginRequest) {
    // 로그인 서비스 호출
    ManagerDto loggedInManager = joinService.login(loginRequest);
    return new LoginDto.Response(loggedInManager.getEmail(), loggedInManager.getRole());
  }
}
