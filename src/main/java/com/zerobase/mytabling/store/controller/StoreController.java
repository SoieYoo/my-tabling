package com.zerobase.mytabling.store.controller;

import com.zerobase.mytabling.store.dto.StoreDto;
import com.zerobase.mytabling.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StoreController {

  private final StoreService storeService;

  /**
   * 매장 등록
   */
  @PostMapping("/store")
  public ResponseEntity<String> registerStore(@RequestBody @Valid StoreDto.Request storeRequest) {
    Long savedId = storeService.registerStore(storeRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Store registered successfully , id = " + savedId);
  }

}
