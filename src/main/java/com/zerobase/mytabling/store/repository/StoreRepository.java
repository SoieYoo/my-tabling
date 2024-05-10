package com.zerobase.mytabling.store.repository;

import com.zerobase.mytabling.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
