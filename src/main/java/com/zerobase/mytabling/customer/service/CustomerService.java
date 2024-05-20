package com.zerobase.mytabling.customer.service;

import static com.zerobase.mytabling.type.ErrorCode.USER_NOT_FOUND;
import static com.zerobase.mytabling.type.ErrorCode.USER_PASSWORD_UN_MATCH;

import com.zerobase.mytabling.customer.domain.Customer;
import com.zerobase.mytabling.customer.dto.CustomerDto;
import com.zerobase.mytabling.customer.dto.LoginDto;
import com.zerobase.mytabling.customer.repository.CustomerRepository;
import com.zerobase.mytabling.exception.MyTablingException;
import com.zerobase.mytabling.type.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 회원가입
   */
  public void createCustomer(CustomerDto.Request request) {
    //중복 이메일 검증
    if (isDuplicateEmail(request)) {
      throw new MyTablingException(ErrorCode.ALREADY_EXIST_EMAIL);
    }

    // 비밀번호 암호화
    String hashedPassword = passwordEncoder.encode(request.getPassword());

    Customer customer = Customer.createCustomer(request, hashedPassword);

    customerRepository.save(customer);

  }

  /**
   * 중복 이메일 검증
   */
  private boolean isDuplicateEmail(CustomerDto.Request request) {
    return customerRepository.findByEmail(request.getEmail()).isPresent();
  }

  /**
   * 로그인
   */
  public void login(LoginDto.Request request) {
    // 이메일로 사용자 조회
    Customer customer = customerRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new MyTablingException(USER_NOT_FOUND));

    // 비밀번호 확인
    if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
      throw new MyTablingException(USER_PASSWORD_UN_MATCH);
    }
  }

}
