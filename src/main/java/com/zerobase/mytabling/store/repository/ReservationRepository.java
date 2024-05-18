package com.zerobase.mytabling.store.repository;

import com.zerobase.mytabling.store.domain.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  List<Reservation> findByStoreIdAndReservationDateTimeBetween(Long storeId, LocalDateTime start,
      LocalDateTime end);

}
