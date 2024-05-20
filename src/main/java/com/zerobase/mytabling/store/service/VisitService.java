package com.zerobase.mytabling.store.service;

import com.zerobase.mytabling.store.domain.Reservation;
import com.zerobase.mytabling.store.repository.ReservationRepository;
import com.zerobase.mytabling.store.type.ReservationStatus;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitService {

  private final ReservationRepository reservationRepository;

  private static final Duration MINUTES_TO_APPROVE = Duration.ofMinutes(15);

  /**
   * 방문 승인
   */
  public boolean checkAndRefuseVisit(Long reservationId) {

    Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Reservation with ID " + reservationId + " not found"));

    // 승인건인지
    isApproved(reservation);

    LocalDateTime reservationTime = reservation.getReservationDateTime();
    LocalDateTime currentTime = LocalDateTime.now();

    // 15분 이내 도착시 방문 승인
    Duration timeUntilReservation = Duration.between(currentTime, reservationTime);

    if (timeUntilReservation.compareTo(MINUTES_TO_APPROVE) <= 0) {
      // 방문 승인
      return true;
    } else {
      // 방문 거절
      return false;
    }
  }

  private static void isApproved(Reservation reservation) {    // 승인건인지
    if (reservation.getReservationStatus() == ReservationStatus.REJECTED) {
      throw new IllegalStateException("Cannot approve a rejected reservation");
    }
    if (reservation.getReservationStatus() == ReservationStatus.REQUESTED) {
      throw new IllegalStateException("Cannot approve a requested reservation");
    }
    if (reservation.getReservationStatus() == ReservationStatus.CANCELLED) {
      throw new IllegalStateException("Cannot approve a cancelled reservation");
    }
    if (reservation.getReservationStatus() == ReservationStatus.COMPLETED) {
      throw new IllegalStateException("Cannot approve a completed reservation");
    }
  }
}
