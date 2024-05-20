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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

  private final ReservationService reservationService;


  /**
   * 예약 생성
   */
  @PostMapping
  public ResponseEntity<String> createReservation(
      @Valid @RequestBody ReservationDto.Request request) {
    boolean isCreated = reservationService.createReservation(request);
    if (isCreated) {
      return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Requested time slot is not available");
    }
  }

  /**
   * 예약 조회
   */
  @GetMapping("/available-times")
  public List<LocalTime> getAvailableTimes(
      @RequestParam("store_id") Long storeId,
      @RequestParam("date") String date,
      @RequestParam("duration") int duration
  ) {
    LocalDate reservationDate = LocalDate.parse(date);
    return reservationService.getAvailableTimes(storeId, reservationDate, duration);
  }

  /**
   * 고객의 예약 취소
   */
  @PatchMapping("/{reservationId}/cancel")
  public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
    reservationService.cancelReservation(reservationId);
    return ResponseEntity.status(HttpStatus.OK).body("Reservation canceled successfully");
  }

  /**
   * 예약 승인
   */
  @PatchMapping("/{reservationId}/approve")
  public ResponseEntity<String> approveReservation(@PathVariable Long reservationId) {
    reservationService.approveReservation(reservationId);
    return ResponseEntity.status(HttpStatus.OK).body("Reservation approved successfully");
  }

  /**
   * 예약 거절
   */
  @PatchMapping("/{reservationId}/reject")
  public ResponseEntity<String> rejectReservation(@PathVariable Long reservationId) {
      reservationService.rejectReservation(reservationId);
      return ResponseEntity.status(HttpStatus.OK).body("Reservation rejected successfully");
  }


}
