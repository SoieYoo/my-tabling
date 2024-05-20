package com.zerobase.mytabling.manager.service;

import static com.zerobase.mytabling.type.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.zerobase.mytabling.type.ErrorCode.FILED_NOT_EMPTY;
import static com.zerobase.mytabling.type.ErrorCode.USER_NOT_FOUND;
import static com.zerobase.mytabling.type.ErrorCode.USER_PASSWORD_UN_MATCH;

import com.zerobase.mytabling.exception.MyTablingException;
import com.zerobase.mytabling.manager.domain.Manager;
import com.zerobase.mytabling.manager.dto.LoginDto;
import com.zerobase.mytabling.manager.dto.ManagerDto;
import com.zerobase.mytabling.manager.repository.ManagerRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
  private final ManagerRepository managerRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 매장 관리자 회원가입
   */
  public ManagerDto createManager(ManagerDto managerDto) {
    //중복 이메일 검증
    if (isDuplicateEmail(managerDto)) {
      throw new MyTablingException(ALREADY_EXIST_EMAIL);
    }

    if (StringUtils.isBlank(managerDto.getPhoneNumber())) {
      throw new MyTablingException(FILED_NOT_EMPTY);
    }

    //비밀번호 해싱
    String hashedPassword = passwordEncoder.encode(managerDto.getPassword());

    Manager saveCustomer = managerRepository.save(
        Manager.builder()
            .email(managerDto.getEmail())
            .name(managerDto.getName())
            .phoneNumber(managerDto.getPhoneNumber())
            .password(hashedPassword)
            .createdAt(LocalDateTime.now())
            .build()
    );

    return ManagerDto.fromEntity(saveCustomer);
  }
  /**
   * 중복 이메일 검증
   */
  private boolean isDuplicateEmail(ManagerDto managerDto) {
    return managerRepository.findByEmail(managerDto.getEmail()).isPresent();
  }
  /**
   * 로그인
   */
  public ManagerDto login(LoginDto.Request managerDto) {
    // 이메일로 사용자 조회
    Manager manager = managerRepository.findByEmail(managerDto.getEmail())
        .orElseThrow(() -> new MyTablingException(USER_NOT_FOUND));

    // 비밀번호 확인
    if (!passwordEncoder.matches(managerDto.getPassword(), manager.getPassword())) {
      throw new MyTablingException(USER_PASSWORD_UN_MATCH);
    }

    return ManagerDto.fromEntity(manager);
  }

  /**
   * 이메일로 관리자 조회
   */
  public Manager findByEmail(String email) {
    return managerRepository.findByEmail(email)
        .orElse(null);
  }
}
