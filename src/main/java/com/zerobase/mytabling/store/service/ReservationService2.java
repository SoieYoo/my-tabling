package com.zerobase.mytabling.store.service;

import com.zerobase.mytabling.customer.domain.Customer;
import com.zerobase.mytabling.customer.repository.CustomerRepository;
import com.zerobase.mytabling.store.domain.Reservation;
import com.zerobase.mytabling.store.domain.Store;
import com.zerobase.mytabling.store.dto.ReservationDto;
import com.zerobase.mytabling.store.repository.ReservationRepository;
import com.zerobase.mytabling.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService2 {
  private final StoreRepository storeRepository;
  private final CustomerRepository customerRepository;
  private final ReservationRepository reservationRepository;

  /**
   * 예약 등록
   */
  public boolean createReservation(ReservationDto.Request request) {
    // Customer 엔티티와 Store 엔티티를 찾아옴
    Customer customer = customerRepository.findById(request.getCustomerId())
        .orElseThrow(() -> new EntityNotFoundException(
            "Customer not found with id: " + request.getCustomerId()));

    Store store = storeRepository.findById(request.getStoreId())
        .orElseThrow(
            () -> new EntityNotFoundException("Store not found with id: " + request.getStoreId()));

    // 예약 가능한 시간인지 조회
    LocalDate reservationDate = request.getReservationDateTime().toLocalDate();
    LocalTime requestedTime = request.getReservationDateTime().toLocalTime();
    List<LocalTime> availableTimes = getAvailableTimes(store.getId(), reservationDate,
        store.getReservationTimeUnit());

    if (!availableTimes.contains(requestedTime)) {
      return false;
    }

    Reservation reservation = Reservation.createReservation(request, customer, store);
    reservationRepository.save(reservation);
    return true;
  }

  /**
   * 예약 가능 시간 조회
   */
  public List<LocalTime> getAvailableTimes(Long storeId, LocalDate date, int durationMinutes) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new IllegalArgumentException("Store not found"));

    LocalTime openingTime = store.getOpenTime();
    LocalTime closingTime = store.getCloseTime();
    LocalDateTime startOfDay = date.atTime(openingTime);
    LocalDateTime endOfDay = date.atTime(closingTime);

    List<Reservation> reservations = reservationRepository.findByStoreIdAndReservationDateTimeBetween(
        storeId, startOfDay, endOfDay);

    List<LocalTime> availableTimes = new ArrayList<>();
    LocalTime currentTime = openingTime;

    while (!currentTime.plusMinutes(durationMinutes).isAfter(closingTime)) {
      LocalDateTime proposedStartTime = date.atTime(currentTime);
      LocalDateTime proposedEndTime = proposedStartTime.plusMinutes(durationMinutes);

      boolean isAvailable = reservations.stream().noneMatch(reservation ->
          reservation.getReservationDateTime().isBefore(proposedEndTime)
              && reservation.getReservationDateTime().plusMinutes(store.getReservationTimeUnit())
              .isAfter(proposedStartTime)
      );

      if (isAvailable) {
        availableTimes.add(currentTime);
      }

      currentTime = currentTime.plusMinutes(durationMinutes);
    }

    return availableTimes;
  }
}
