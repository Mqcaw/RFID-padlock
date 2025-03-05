package com.capstone.RFID_padlock.Entity.Repository;

import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
