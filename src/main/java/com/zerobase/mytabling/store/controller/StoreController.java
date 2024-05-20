package com.zerobase.mytabling.store.controller;

import com.zerobase.mytabling.store.domain.Store;
import com.zerobase.mytabling.store.dto.StoreDto;
import com.zerobase.mytabling.store.service.StoreService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  /**
   * 매장 목록 조회 - 오름차순
   */
  @GetMapping("/stores/asc")
  public ResponseEntity<List<Store>> getStoresAsc() {
    List<Store> stores = storeService.getStoresSortedByRating(true);
    return ResponseEntity.ok(stores);
  }

  /**
   * 매장 목록 조회 - 내림차순
   */
  @GetMapping("/stores/desc")
  public ResponseEntity<List<Store>> getStoresDesc() {
    List<Store> stores = storeService.getStoresSortedByRating(false);
    return ResponseEntity.ok(stores);
  }

  /**
   * 매장 조회
   */
  @GetMapping("/{storeId}")
  public StoreDto.Response getStoreByStoreId(@PathVariable Long storeId) {
    return storeService.getStoreById(storeId);
  }
  /**
   * 매장 수정
   */
  @PutMapping("/{storeId}")
  public ResponseEntity<String> updateStore(@PathVariable Long storeId, @RequestBody StoreDto.Request storeRequest) {

      storeService.updateStore(storeId,
          storeRequest.getStoreName(),
          storeRequest.getStoreTelNumber(),
          storeRequest.getManagerId());

      return ResponseEntity.status(HttpStatus.OK).body("Store updated successfully");
  }

  /**
   * 매장 삭제
   */
  @DeleteMapping("/{storeId}")
  public ResponseEntity<String> deleteStore(@PathVariable Long storeId) {
      storeService.deleteStore(storeId);
      return ResponseEntity.status(HttpStatus.OK).body("Store deleted successfully");
  }
}
