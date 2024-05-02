package com.zerobase.mytabling.store.domain;

import com.zerobase.mytabling.manager.domain.Manager;
import com.zerobase.mytabling.store.type.ReservationStatus;
import com.zerobase.mytabling.store.type.UserType;
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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
public class ReservationAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private ReservationStatus oldStatus;   // 예약의 이전 상태
  @Enumerated(EnumType.STRING)
  private ReservationStatus newStatus;   // 예약의 새로운 상태

  private LocalDateTime changedAt;
  private String changedByUserId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserType changedByType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_id")
  private Reservation reservation;

  private @CreatedDate LocalDateTime createdAt;
}
