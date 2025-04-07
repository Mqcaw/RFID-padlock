package com.capstone.RFID_padlock.Entity.Repository;

import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM students WHERE key_card_id IS NOT NULL", nativeQuery = true)
    List<Student> findByKeyCardIdIsNotNull();
}
