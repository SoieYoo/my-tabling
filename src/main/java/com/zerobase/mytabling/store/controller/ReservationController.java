package com.zerobase.mytabling.store.controller;

import com.zerobase.mytabling.store.dto.ReservationDto;
import com.zerobase.mytabling.store.service.ReservationService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;


  /**
   * 예약 생성
   */
  @PostMapping("/reservation")
  public ResponseEntity<String> createReservation(
      @Valid @RequestBody ReservationDto.Request request) {
    boolean isCreated = reservationService.createReservation(request);
    if (isCreated) {
      return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested time slot is not available");
    }
  }

  /**
   * 예약 조회
   */
  @GetMapping("/reservation/available-times")
  public List<LocalTime> getAvailableTimes(
      @RequestParam("store_id") Long storeId,
      @RequestParam("date") String date,
      @RequestParam("duration") int duration
  ) {
    LocalDate reservationDate = LocalDate.parse(date);
    return reservationService.getAvailableTimes(storeId, reservationDate, duration);
  }
}
