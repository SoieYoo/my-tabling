package com.zerobase.mytabling.store.repository;

import com.zerobase.mytabling.store.domain.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store, Long> {

  List<Store> findAllByIdInOrderById(List<Long> ids);
  List<Store> findAllByIdIn(List<Long> ids);
}
