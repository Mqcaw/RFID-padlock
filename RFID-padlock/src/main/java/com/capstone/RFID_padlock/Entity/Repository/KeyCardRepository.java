package com.capstone.RFID_padlock.Entity.Repository;

import com.capstone.RFID_padlock.Entity.KeyCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyCardRepository extends JpaRepository<KeyCard, Long> {
}
