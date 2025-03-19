package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Lock;
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
        Student student = getEntity(studentId);
        KeyCard keyCard = keyCardService.getEntity(keyCardId);
        Student previousStudent = getEntity(keyCard.getStudentId());

        student.setKeyCardId(keyCardId);
        keyCard.setStudentId(studentId);
        previousStudent.setKeyCardId(null);
        save(student);
        save(previousStudent);
        keyCardService.save(keyCard);
    }

    public List<Student> getStudentsWhereKeyCardIdIsNotNull() {
        return studentRepository.findByKeyCardIdIsNotNull();
    }



}
