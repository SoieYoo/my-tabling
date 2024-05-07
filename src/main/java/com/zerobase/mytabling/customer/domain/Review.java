package com.zerobase.mytabling.customer.domain;

import com.zerobase.mytabling.store.domain.Reservation;
import com.zerobase.mytabling.store.domain.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @OneToOne
  @JoinColumn(name = "reservation_id", nullable = false, unique = true)
  private Reservation reservation;

  @Column(nullable = false)
  private String comment;

  @Column(nullable = false)
  private double rating;

  @Column(name = "created_at", nullable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime modifiedAt;
}
