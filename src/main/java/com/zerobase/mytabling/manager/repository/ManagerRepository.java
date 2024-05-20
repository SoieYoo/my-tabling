package com.zerobase.mytabling.manager.repository;

import com.zerobase.mytabling.manager.domain.Manager;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

  Optional<Manager> findByEmail(String email);
}
