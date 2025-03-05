package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Repository.KeyCardRepository;
import com.capstone.RFID_padlock.Entity.Repository.StudentRepository;
import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements ServiceInterface<Student> {

    private final StudentRepository studentRepository;
    private KeyCardService keyCardService;

    @Autowired
    public StudentService(StudentRepository studentRepository, KeyCardService keyCardService) {
        this.studentRepository = studentRepository;
        this.keyCardService = keyCardService;
    }

    @Override
    public Student addEntity(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllEntities() {
        return studentRepository.findAll();
    }

    @Override
    public Student  getEntity(Long id) {
        if (studentRepository.findById(id).isPresent()) {
            return studentRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(getEntity(id));
    }

    public void assignKeyCard(Long studentId, Long keyCardId){
        getEntity(studentId).setKeyCardId(keyCardId);
        keyCardService.getEntity(keyCardId).setStudentId(studentId);
    }



}
