package com.zerobase.mytabling.store.controller;

import com.zerobase.mytabling.store.service.ReservationService;
import com.zerobase.mytabling.store.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VisitController {

  private final VisitService visitService;
  private final ReservationService reservationService;

  /**
   * 방문 확인
   */
  @PostMapping("/visit/{reservationId}/confirm")
  public ResponseEntity<String> checkAndRefuseVisit(@PathVariable Long reservationId) {
    boolean isVisitAllowed = visitService.checkAndRefuseVisit(reservationId);
    if (isVisitAllowed) {
      reservationService.completedReservation(reservationId);
      return ResponseEntity.status(HttpStatus.OK).body("Visit allowed");
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Visit refused (Arrived more than 10 minutes late)");
    }
  }

}
