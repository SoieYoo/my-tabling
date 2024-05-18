package com.zerobase.mytabling.store.domain;

import com.zerobase.mytabling.customer.domain.Customer;
import com.zerobase.mytabling.customer.domain.Review;
import com.zerobase.mytabling.store.dto.ReservationDto;
import com.zerobase.mytabling.store.type.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "reservation")
  private Review review;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @Column(nullable = false)
  private LocalDateTime reservationDateTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReservationStatus reservationStatus;

  private int bookingPeopleCount;
  private LocalDateTime visitConfirmAt;

  public static Reservation createReservation(ReservationDto.Request request, Customer customer, Store store) {
    return Reservation.builder()
        .customer(customer)
        .store(store)
        .reservationDateTime(request.getReservationDateTime())
        .reservationStatus(ReservationStatus.REQUESTED)
        .bookingPeopleCount(request.getBookingPeopleCount())
        .build();
  }
}
