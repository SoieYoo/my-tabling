package com.zerobase.mytabling.manager.repository;

import com.zerobase.mytabling.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
