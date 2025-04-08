package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.KeyCard;
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
        if (keyCard.getStudentId() != null) {
            Student previousStudent = getEntity(keyCard.getStudentId());
            previousStudent.setKeyCardId(null);
            save(previousStudent);
        }

        student.setKeyCardId(keyCardId);
        keyCard.setStudentId(studentId);
        save(student);
        keyCardService.save(keyCard);
    }

    public Student synchronize(Long studentId) {
        Student student = getEntity(studentId);
        if (student.getKeyCardId() != null) {
            assignKeyCard(student.getId(), student.getKeyCardId());
        }

        return save(student);
    }

    public Student synchronize(Student student) {
        if (student.getId() == null || getEntity(student.getId()) == null) {
            return null;
        }
        if (student.getKeyCardId() != null) {
            assignKeyCard(student.getId(), student.getKeyCardId());
        }

        return save(student);
    }

    public Student synchronizeAdd(Student student) {
        Student newStudent = addEntity(student);
        if (newStudent.getKeyCardId() != null) {
            assignKeyCard(newStudent.getId(), newStudent.getKeyCardId());
        };
        return save(newStudent);
    }

    public List<Student> getStudentsWhereKeyCardIdIsNotNull() {
        return studentRepository.findByKeyCardIdIsNotNull();
    }



}
