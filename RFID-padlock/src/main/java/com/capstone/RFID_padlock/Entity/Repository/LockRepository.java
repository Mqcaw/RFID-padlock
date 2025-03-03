package com.capstone.RFID_padlock.Entity.Repository;

import com.capstone.RFID_padlock.Entity.Lock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockRepository extends JpaRepository<Lock, Long> {
}
